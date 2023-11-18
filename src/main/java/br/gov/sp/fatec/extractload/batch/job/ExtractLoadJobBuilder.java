package br.gov.sp.fatec.extractload.batch.job;

import br.gov.sp.fatec.extractload.batch.reader.ExtractJdbcPagingItemReader;
import br.gov.sp.fatec.extractload.batch.writer.CompositeJdbcPagingItemWriter;
import br.gov.sp.fatec.extractload.batch.writer.InsertJdbcItemWriter;
import br.gov.sp.fatec.extractload.batch.writer.LoadItemWriterClassifier;
import br.gov.sp.fatec.extractload.batch.writer.UpdateJdbcItemWriter;
import br.gov.sp.fatec.extractload.config.component.BatchExecutionProps;
import br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager;
import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static br.gov.sp.fatec.extractload.utils.Constants.JOB_NAME;
import static br.gov.sp.fatec.extractload.utils.Constants.STEP_NAME;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.textNormalizer;
import static org.springframework.batch.core.ExitStatus.COMPLETED;
import static org.springframework.batch.core.ExitStatus.FAILED;
import static org.springframework.batch.core.ExitStatus.NOOP;

@Slf4j
@Configuration
public class ExtractLoadJobBuilder {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final BatchExecutionProps batchExecutionProps;

    private final DataSourceRoutingManager datasourceRoutingManager;

    private final ApplicationContext applicationContext;

    public ExtractLoadJobBuilder(final JobRepository jobRepository,
        final PlatformTransactionManager platformTransactionManager,
        final BatchExecutionProps batchExecutionProps,
        final DataSourceRoutingManager datasourceRoutingManager,
        ApplicationContext applicationContext) {
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.batchExecutionProps = batchExecutionProps;
        this.datasourceRoutingManager = datasourceRoutingManager;
        this.applicationContext = applicationContext;
    }

    public Job build(final DataBundleDto dataBundleDto) {
        final var jobName = JOB_NAME.concat(textNormalizer(dataBundleDto.dataBundleName()));
        return new JobBuilder(jobName, jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(getStepsFlow(jobName, dataBundleDto))
            .end()
            .build();
    }

    @SneakyThrows(SQLException.class)
    private Flow getStepsFlow(final String jobName, final DataBundleDto dataBundleDto) {
        log.info("Building Job Flow [{}]", jobName);

        var sourceDataSource = datasourceRoutingManager.getInstance(dataBundleDto.sourceDataSourceId());
        var targetDataSource = datasourceRoutingManager.getInstance(dataBundleDto.targetDataSourceId());
        List<Step> steps = buildSteps(sourceDataSource, targetDataSource, dataBundleDto.bundledAppTables());

        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>(jobName);
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

    private List<Step> buildSteps(final DataSource sourceDataSource, final DataSource targetDataSource, final List<BundledAppTableDto> tables) {
        return tables.stream()
            .map(table -> buildStep(sourceDataSource, targetDataSource, table))
            .toList();
    }

    private Step buildStep(final DataSource sourceDataSource, final DataSource targetDataSource, final BundledAppTableDto bundledAppTable) {
        final var sourceTableName = textNormalizer(bundledAppTable.sourceAppTableName());
        final var targetTableName = textNormalizer(bundledAppTable.targetAppTableName());
        final var stepName = String.format(STEP_NAME, sourceTableName, targetTableName);
        log.info("Building Step: [{}]", stepName);

        final var reader = applicationContext.getBean(ExtractJdbcPagingItemReader.class, sourceDataSource, batchExecutionProps.pageSize(),
            batchExecutionProps.fetchSize(), bundledAppTable);

        final var insertWriter = applicationContext.getBean(InsertJdbcItemWriter.class, targetDataSource, targetTableName);
        final var updateWriter = applicationContext.getBean(UpdateJdbcItemWriter.class, targetDataSource, targetTableName);
        final var writerClassifier = applicationContext.getBean(CompositeJdbcPagingItemWriter.class,
            new LoadItemWriterClassifier(insertWriter, updateWriter));

        return new StepBuilder(stepName, jobRepository)
            .<RowMappedDto, RowMappedDto>chunk(batchExecutionProps.chunkSize(), platformTransactionManager)
            .reader(reader)
            .writer(writerClassifier)
            .faultTolerant()
            .retryLimit(batchExecutionProps.retryLimit())
            .retry(PessimisticLockingFailureException.class)
            .taskExecutor(stepTaskExecutor(batchExecutionProps.concurrencyLimit()))
            .build();
    }

    private static TaskExecutor stepTaskExecutor(final int concurrencyLimit) {
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(concurrencyLimit);
        simpleAsyncTaskExecutor.setThreadNamePrefix("Task-Exec-");
        return simpleAsyncTaskExecutor;
    }

}
