package br.gov.sp.fatec.extractload.service;

import br.gov.sp.fatec.extractload.api.model.CreatedObjectResponse;
import br.gov.sp.fatec.extractload.batch.job.ExtractLoadJobBuilder;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Slf4j
@Service
@Import(ExtractLoadJobBuilder.class)
public class BatchExecutionService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExplorer jobExplorer;

    @Autowired
    private ExtractLoadJobBuilder jobBuilder;

    public CreatedObjectResponse startJob(JobParametersDto jobParams) {
        String uuid = UUID.randomUUID().toString();
        Long bundleId = jobParams.getDataBundleId();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("uuid", uuid)
                .addLong("dataBundleId", bundleId)
                .addString("extractionType", jobParams.getExtractionType().getValue())
                .addString("executionDateTime", LocalDateTime.now().toString())
                .toJobParameters();

        try {
            Job job = jobBuilder.job(jobParams);
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            log.error("BatchExecutionService error " + e.getMessage());
        }

        return new CreatedObjectResponse().uid(uuid);
    }

    public JobExecution findJobExecutionById(Long jobExecutionId) {
        return jobExplorer.getJobExecution(jobExecutionId);
    }

    public JobExecution findJobExecutionByJobName(String jobName) {
        return jobExplorer.getLastJobExecution(findLastJobInstanteByJobName(jobName));
    }

    public JobInstance findLastJobInstanteByJobName(String jobName) {
        JobInstance jobInstance = jobExplorer.getLastJobInstance(jobName);
        if (isNull(jobInstance)) {
            throw new UnprocessableEntityException("");
        }
        return jobInstance;
    }

}
