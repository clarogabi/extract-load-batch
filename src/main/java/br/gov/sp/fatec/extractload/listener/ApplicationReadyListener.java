package br.gov.sp.fatec.extractload.listener;

import br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager;
import br.gov.sp.fatec.extractload.domain.dto.DataSourceDto;
import br.gov.sp.fatec.extractload.service.DataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager.buildHikariConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyListener {

    private final DataSourceService dataSourceService;

    private final DataSourceRoutingManager dataSourceRoutingManager;

    @EventListener
    public void onReady(final ApplicationReadyEvent applicationReadyEvent) {
        log.info("Application has started in {}s. Handling routing database connections.", applicationReadyEvent.getTimeTaken().toSeconds());
        List<DataSourceDto> dataSources = dataSourceService.findAllDataSources();

        for (DataSourceDto dataSourceDto : dataSources) {
            try {
                dataSourceRoutingManager.addConnection(dataSourceDto.uid(), buildHikariConfig(dataSourceDto));
                log.info("Loaded data source of instance ID [{}] under connection [{}].", dataSourceDto.uid(), dataSourceDto.getJdbcUrl());
            } catch (SQLException e) {
                log.error("Could not load data source under connection [{}].", dataSourceDto.getJdbcUrl(), e);
            }
        }
    }

}
