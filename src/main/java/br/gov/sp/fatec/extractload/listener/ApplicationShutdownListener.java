package br.gov.sp.fatec.extractload.listener;

import br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationShutdownListener {

    private final DataSourceRoutingManager dataSourceRoutingManager;

    @PreDestroy
    public void onDestroy() {
        log.info("Application shutdown. Handling routing database connections.");
        dataSourceRoutingManager.removeAllConnections();
    }

}
