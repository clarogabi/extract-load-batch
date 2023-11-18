package br.gov.sp.fatec.extractload.config.datasource.router;

import br.gov.sp.fatec.extractload.domain.dto.DataSourceDto;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class DataSourceRoutingManager {

    private static final ThreadLocal<Long> CURRENT_INSTANCE = new ThreadLocal<>();
    private final Map<Object, Object> dataSourcesInstances = new ConcurrentHashMap<>();
    private final Map<Long, DataSourceProperties> dataSourcesProps = new ConcurrentHashMap<>();
    private final AbstractRoutingDataSource dataSourceRouting = new AbstractRoutingDataSource() {
        @Override
        protected Object determineCurrentLookupKey() {
            return CURRENT_INSTANCE.get();
        }
    };

    @Bean
    public DataSource dataSourceRouting(@Qualifier("extractLoadDataSource") final DataSource extractLoadDataSource) {
        dataSourceRouting.setTargetDataSources(dataSourcesInstances);
        dataSourceRouting.setDefaultTargetDataSource(extractLoadDataSource);
        dataSourceRouting.afterPropertiesSet();
        return dataSourceRouting;
    }

    public DataSource getInstance(Long instanceId) throws SQLException {
        if (instanceIsAbsent(instanceId)) {
            if (dataSourcesProps.containsKey(instanceId)) {
                DataSourceProperties properties;
                try {
                    properties = dataSourcesProps.get(instanceId);
                    log.info("Data source properties resolved for instance ID [{}].", instanceId);
                } catch (Exception e) {
                    log.error("Could not resolve the data source connection.", e);
                    throw new UnprocessableEntityProblem("Não foi possível resolver a conexão com o banco de dados.");
                }
                addConnection(instanceId, properties);
            } else {
                log.error("Datasource connection ID [{}] not found.", instanceId);
                throw new NotFoundProblem("Registro não encontrado!");
            }
        }
        return (DataSource) dataSourcesInstances.get(instanceId);
    }

    public void replaceConnection(Long instanceId, DataSourceProperties dataSourceProperties) throws SQLException {
        final var dataSource = createDataSource(dataSourceProperties);

        try (final var conn = dataSource.getConnection()) {
            dataSourcesInstances.replace(instanceId, dataSource);
            dataSourcesProps.replace(instanceId, dataSourceProperties);
            dataSourceRouting.afterPropertiesSet();
            log.info("Data source ID [{}] successfully replaced.", instanceId);
        }
    }

    public void addConnection(Long instanceId, DataSourceProperties dataSourceProperties) throws SQLException {
        final var dataSource = createDataSource(dataSourceProperties);

        try (final var conn = dataSource.getConnection()) {
            dataSourcesInstances.put(instanceId, dataSource);
            dataSourcesProps.put(instanceId, dataSourceProperties);
            dataSourceRouting.afterPropertiesSet();
            log.info("Data source ID [{}] successfully connected.", instanceId);
        }
    }

    public static DataSourceProperties buildDataSourceProperties(DataSourceDto dataSourceDto) {
        final var properties = new DataSourceProperties();
        properties.setName(dataSourceDto.databaseName());
        properties.setUrl(dataSourceDto.getJdbcUrl());
        properties.setUsername(dataSourceDto.userName());
        properties.setPassword(dataSourceDto.password());
        properties.setDriverClassName(dataSourceDto.databaseDriver().getClassName());
        return properties;
    }

    private static DataSource createDataSource(DataSourceProperties dataSourceProperties) {
        return DataSourceBuilder.create()
            .driverClassName(dataSourceProperties.getDriverClassName())
            .url(dataSourceProperties.getUrl())
            .username(dataSourceProperties.getUsername())
            .password(dataSourceProperties.getPassword())
            .build();
    }

    public void removeConnection(Long instanceId) {
        dataSourcesInstances.remove(instanceId);
        dataSourcesProps.remove(instanceId);
        dataSourceRouting.afterPropertiesSet();
    }

    public boolean instanceIsAbsent(Long instanceId) {
        return !dataSourcesInstances.containsKey(instanceId);
    }

    public void unload() {
        CURRENT_INSTANCE.remove();
    }

}
