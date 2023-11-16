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

import static br.gov.sp.fatec.extractload.config.datasource.router.DataSourceRoutingManager.buildDataSourceProperties;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyListener {

    private final DataSourceService dataSourceService;

    private final DataSourceRoutingManager dataSourceRoutingManager;

    @EventListener
    public void onReady(final ApplicationReadyEvent applicationReadyEvent) {
        log.info("Application has started [{}]. Handling routing database connections.", applicationReadyEvent.getTimeTaken());
        List<DataSourceDto> dataSources = dataSourceService.findAllDataSources();

        for (DataSourceDto dataSourceDto : dataSources) {
            try {
                var dataSourceProperties = buildDataSourceProperties(dataSourceDto);
                dataSourceRoutingManager.addConnection(dataSourceDto.getUid(), dataSourceProperties);
                log.info("Loaded data source for instance [{} - {}].", dataSourceDto.getUid(), dataSourceDto.getDatabaseName());
            } catch (SQLException e) {
                log.error("Could not load data source for instance [{} - {}]!", dataSourceDto.getUid(), dataSourceDto.getDatabaseName(), e);
            }
        }
    }

}
