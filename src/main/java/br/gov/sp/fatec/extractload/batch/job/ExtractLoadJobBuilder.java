package br.gov.sp.fatec.extractload.batch.job;

import br.gov.sp.fatec.extractload.api.model.ExtractionTypeEnum;
import br.gov.sp.fatec.extractload.batch.reader.ExtractJdbcPagingItemReader;
import br.gov.sp.fatec.extractload.batch.validator.ExtractLoadJobParametersValidator;
import br.gov.sp.fatec.extractload.batch.writer.CompositeJdbcPagingItemWriter;
import br.gov.sp.fatec.extractload.batch.writer.InsertJdbcItemWriter;
import br.gov.sp.fatec.extractload.batch.writer.LoadItemWriterClassifier;
import br.gov.sp.fatec.extractload.batch.writer.UpdateJdbcItemWriter;
import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import br.gov.sp.fatec.extractload.service.BundledAppTableService;
import br.gov.sp.fatec.extractload.service.DataBundleService;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static br.gov.sp.fatec.extractload.utils.Constants.JOB_NAME;
import static java.util.Objects.isNull;
import static org.springframework.batch.core.ExitStatus.COMPLETED;
import static org.springframework.batch.core.ExitStatus.FAILED;
import static org.springframework.batch.core.ExitStatus.NOOP;

@Slf4j
@Component
@EnableBatchProcessing
public class ExtractLoadJobBuilder {

    @Value("${batch.execution.concurrencyLimit:2}")
    public Integer concurrencyLimit;

    @Value("${batch.execution.chunkSize:10}")
    public Integer chunkSize;

    @Value("${batch.execution.reader.fetchSize:100}")
    public Integer fetchSize;

    @Value("${batch.execution.retry.limit:3}")
    public Integer retryLimit;

    @Autowired
    public ApplicationContext context;

    @Autowired
    private DataBundleService dataBundleService;

    @Autowired
    private BundledAppTableService bundledAppTableService;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("sourceJdbcUtils")
    private JdbcUtils sourceJdbcUtils;

    @Autowired
    @Qualifier("targetJdbcUtils")
    private JdbcUtils targetJdbcUtils;

    @Autowired
    @Qualifier("sourceDataSource")
    private DataSource sourceDataSource;

    @Autowired
    @Qualifier("targetDataSource")
    private DataSource targetDataSource;

    @Autowired
    @Qualifier("targetNamedParameterJdbcTemplate")
    private NamedParameterJdbcTemplate targetJdbcTemplate;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private ExtractLoadJobParametersValidator jobParametersValidator;

    public Job job(JobParametersDto jobParams) {
        var dataBundleId = jobParams.getDataBundleId();
        var bundleName = dataBundleService.findDataBundleNameById(dataBundleId);

        return jobBuilderFactory
                .get(JOB_NAME.concat(bundleName).toUpperCase())
                .validator(jobParametersValidator)
                .incrementer(new RunIdIncrementer())
                .start(getStepsFlow(bundleName, dataBundleId, ExtractionTypeEnum.fromValue(jobParams.getExtractionType().getValue())))
                .end()
                .build();
    }

    public Flow getStepsFlow(String bundleName, Long dataBundleId, ExtractionTypeEnum extractionType) {
        var flowName = JOB_NAME.concat(bundleName).toUpperCase();
        log.info("Building Job Flow [{}]", flowName);
        List<Step> steps = buildSteps(getTables(dataBundleId), extractionType);

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>(flowName);

        for (Step step : steps) {
            if (0 == steps.indexOf(step)) {
                flowBuilder.start(step);
            } else {
                flowBuilder.next(step);
            }
        }

        return flowBuilder
                .on(COMPLETED.getExitCode()).end()
                .on(NOOP.getExitCode()).end()
                .on(FAILED.getExitCode()).fail()
                .end();
    }

    private List<BundledAppTableDto> getTables(Long dataBundleId) {
        return bundledAppTableService.findBundledAppTablesByDataBundleId(dataBundleId);
    }

    public List<Step> buildSteps(List<BundledAppTableDto> tables, ExtractionTypeEnum extractionType) {

        if (isNull(tables) || 0 == tables.size()) {
            throw new UnprocessableEntityProblem("Pacote de extração e carregamento de dados deve conter ao menos uma tabela!");
        }

        //TODO: check if table exists in source datasource from jdbc metadata
        //TODO: check if table exists in target datasource from jdbc metadata

        return tables.stream()
                .map(table -> step(table, extractionType))
                .collect(Collectors.toList());
    }

    public Step step(BundledAppTableDto bundledAppTableDto, ExtractionTypeEnum extractionType) {
        StringBuilder stepName = new StringBuilder();
        stepName.append("EXTRACT_LOAD_DATA_STEP-");
        stepName.append(bundledAppTableDto.getSourceAppTableName().trim().toUpperCase());

        log.info("Building Step: [{}]", stepName);

        ExtractJdbcPagingItemReader reader = context.getBean(ExtractJdbcPagingItemReader.class,
                sourceDataSource, sourceJdbcUtils, targetJdbcTemplate, fetchSize, bundledAppTableDto, extractionType);

        InsertJdbcItemWriter insertWriter = context.getBean(InsertJdbcItemWriter.class, targetDataSource, targetJdbcUtils,
                bundledAppTableDto.getTargetAppTableName());

        UpdateJdbcItemWriter updateWriter = context.getBean(UpdateJdbcItemWriter.class, targetDataSource, targetJdbcUtils,
                bundledAppTableDto.getTargetAppTableName());

        CompositeJdbcPagingItemWriter writerClassifier = context.getBean(CompositeJdbcPagingItemWriter.class,
                new LoadItemWriterClassifier(insertWriter, updateWriter));

        return stepBuilderFactory
                .get(stepName.toString())
                .<RowMappedDto, RowMappedDto>chunk(chunkSize)
                .reader(reader)
                .writer(writerClassifier)
                .faultTolerant()
                .retryLimit(retryLimit)
                .retry(DeadlockLoserDataAccessException.class)
                .taskExecutor(stepTaskExecutor())
                .build();
    }

    public TaskExecutor stepTaskExecutor() {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(concurrencyLimit);
        simpleAsyncTaskExecutor.setThreadNamePrefix("Step-Exec-");
        return simpleAsyncTaskExecutor;
    }

}
