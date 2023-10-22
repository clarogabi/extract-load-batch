package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.batch.job.ExtractLoadJobBuilder;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Import(ExtractLoadJobBuilder.class)
public class BatchExecutionService {

    private final JobLauncher jobLauncher;

    private final JobExplorer jobExplorer;

    private final ExtractLoadJobBuilder jobBuilder;

    public CreatedObjectResponse startJob(JobParametersDto jobParams) {
        var jobParameters = new JobParametersBuilder()
                .addString("uuid", UUID.randomUUID().toString())
                .addLong("dataBundleId", jobParams.getDataBundleId())
                .addString("extractionType", jobParams.getExtractionType().getValue())
                .addString("executionDateTime", LocalDateTime.now().toString())
                .toJobParameters();

        var job = jobBuilder.job(jobParams);
        try {
            var jobExecution = jobLauncher.run(job, jobParameters);
            return new CreatedObjectResponse().uid(jobExecution.getId().toString());
        } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException | JobInstanceAlreadyCompleteException | JobRestartException e) {
            log.error("Job batch execution error {}", e.getMessage(), e);
            throw new UnprocessableEntityProblem(format("Não foi possível executar o Job devido ao erro [%s].", e.getMessage()));
        }

    }

    public JobExecution findJobExecutionById(Long jobExecutionId) {
        return jobExplorer.getJobExecution(jobExecutionId);
    }

}
