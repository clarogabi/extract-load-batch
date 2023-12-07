package br.gov.sp.fatec.extractload.batch.listener;

import br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletedListener implements JobExecutionListener {

    private final DataSourceRoutingManager dataSourceRoutingManager;

    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        log.info("Clearing connections.");
        dataSourceRoutingManager.clearConnections();
    }

}
