package br.gov.sp.fatec.extractload.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class ExtractLoadBatchJobListener implements JobExecutionListener {

    private static final Logger LOG = LoggerFactory.getLogger(ExtractLoadBatchJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        LOG.info("!!! BEFORE JOB EXECUTION");
    }

    //TODO
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (BatchStatus.COMPLETED.equals(jobExecution.getStatus())) {
            LOG.info("!!! JOB FINISHED! Time to verify the results");
        } else {
            LOG.info("!!! JOB NOT FINISHED! Check what went wrong");
        }
    }
}
