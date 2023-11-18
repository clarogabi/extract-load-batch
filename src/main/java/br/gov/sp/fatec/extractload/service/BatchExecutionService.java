package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.batch.job.ExtractLoadJobBuilder;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
@Import(ExtractLoadJobBuilder.class)
public class BatchExecutionService {

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;
    private final DataBundleService dataBundleService;
    private final ExtractLoadJobBuilder jobBuilder;

    public CreatedObjectResponse startJob(final JobParametersDto jobParams) {

        final var dataBundleId = jobParams.dataBundleId();
        log.info("Processing input parameters validation and enrichment for Job execution: package ID [{}].", dataBundleId);
        final var dataBundle = dataBundleService.findDataBundleById(dataBundleId);
        final var tables = dataBundle.bundledAppTables();

        if (isNull(tables) || tables.isEmpty()) {
            throw new UnprocessableEntityProblem("Pacote de extração e carregamento de dados deve conter ao menos uma tabela.");
        }

        final var bundleName = dataBundle.dataBundleName();
        final var jobParameters = new JobParametersBuilder()
            .addString("uuid", UUID.randomUUID().toString())
            .addLong("dataBundleId", jobParams.dataBundleId())
            .addString("dataBundleName", bundleName)
            .addString("executionDateTime", LocalDateTime.now().toString())
            .toJobParameters();

        log.info("Building Job structure for data extraction and loading batch processing of package [{} - {}].", dataBundleId, bundleName);
        final var job = jobBuilder.build(dataBundle);

        try {
            log.info("Job of package [{} - {}] is ready to start.", dataBundleId, bundleName);
            final var jobExecution = jobLauncher.run(job, jobParameters);
            return new CreatedObjectResponse().uid(jobExecution.getId());
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException | JobRestartException e) {
            log.error("Job batch execution error [{}].", e.getMessage(), e);
            throw new UnprocessableEntityProblem(format("Não foi possível executar o Job devido ao erro [%s].", e.getMessage()));
        }

    }

    public JobExecution findJobExecutionById(final Long jobExecutionId) {
        final var jobExecution =  jobExplorer.getJobExecution(jobExecutionId);

        if (isNull(jobExecution)) {
            throw new NotFoundProblem("Registro não encontrado.");
        }

        return jobExecution;
    }

}
