package br.gov.sp.fatec.extractload.config.datasource.router;

import br.gov.sp.fatec.extractload.domain.dto.DataSourceDto;
import br.gov.sp.fatec.extractload.exception.NotFoundProblem;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.util.StringUtils.hasLength;

@Slf4j
@Configuration
public class DataSourceRoutingManager {

    private static final Long DEFAULT = 0L;
    private static final ThreadLocal<Long> CURRENT_INSTANCE = new ThreadLocal<>();
    private final Map<Object, Object> dataSourcesInstances = new ConcurrentHashMap<>();
    private final Map<Long, HikariConfig> dataSourcesProps = new ConcurrentHashMap<>();

    private final AbstractRoutingDataSource dataSourceRouting = new AbstractRoutingDataSource() {

        @Override
        protected Object determineCurrentLookupKey() {
            return CURRENT_INSTANCE.get();
        }
    };

    @Bean
    public DataSource dataSourceRouting(@Qualifier("extractLoadDataSource") final DataSource extractLoadDataSource) {
        dataSourcesInstances.put(DEFAULT, extractLoadDataSource);
        CURRENT_INSTANCE.set(DEFAULT);
        dataSourceRouting.setTargetDataSources(dataSourcesInstances);
        dataSourceRouting.setDefaultTargetDataSource(extractLoadDataSource);
        dataSourceRouting.afterPropertiesSet();
        log.info("Datasource router successfully initialized [{}].", dataSourcesInstances);
        return dataSourceRouting;
    }

    public HikariDataSource getInstance(Long instanceId) throws SQLException {
        if (instanceIsAbsent(instanceId)) {
            if (dataSourcesProps.containsKey(instanceId)) {
                HikariConfig hikariConfig;
                try {
                    hikariConfig = dataSourcesProps.get(instanceId);
                    log.info("Data source properties resolved for instance ID [{}].", instanceId);
                } catch (Exception e) {
                    log.error("Could not resolve the data source connection.", e);
                    throw new UnprocessableEntityProblem("Não foi possível resolver a conexão com o banco de dados.");
                }
                addConnection(instanceId, hikariConfig);
            } else {
                log.error("Datasource connection ID [{}] not found.", instanceId);
                throw new NotFoundProblem("Registro não encontrado!");
            }
        }
        return (HikariDataSource) dataSourcesInstances.get(instanceId);
    }

    public void replaceConnection(Long instanceId, HikariConfig hikariConfig) throws SQLException {
        final var dataSource = createDataSource(hikariConfig);

        try (final var ignored = dataSource.getConnection()) {
            dataSourcesInstances.replace(instanceId, dataSource);
            dataSourcesProps.replace(instanceId, hikariConfig);
            dataSourceRouting.afterPropertiesSet();
            log.info("Data source ID [{}] successfully replaced.", instanceId);
            log.info("Datasource router refreshed [{}].", dataSourcesInstances);
        }
    }

    public void addConnection(Long instanceId, HikariConfig hikariConfig) throws SQLException {
        final var dataSource = createDataSource(hikariConfig);

        try (final var ignored = dataSource.getConnection()) {
            dataSourcesInstances.put(instanceId, dataSource);
            dataSourcesProps.put(instanceId, hikariConfig);
            dataSourceRouting.afterPropertiesSet();
            log.info("Data source ID [{}] successfully connected.", instanceId);
            log.info("Datasource router refreshed [{}].", dataSourcesInstances);
        }
    }

    public static HikariConfig buildHikariConfig(DataSourceDto dataSourceDto) {
        HikariConfig config = new HikariConfig();

        config.setInitializationFailTimeout(0);
        config.setMaximumPoolSize(40);
        config.setMinimumIdle(40);
        config.setJdbcUrl(dataSourceDto.getJdbcUrl());
        config.setDriverClassName(dataSourceDto.databaseDriver().getClassName());
        config.setUsername(dataSourceDto.userName());
        config.setPassword(dataSourceDto.password());

        final var schema = dataSourceDto.databaseSchema();
        if (hasLength(schema)) {
            config.setSchema(schema);
        }

        return config;
    }

    private static DataSource createDataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    public void removeConnection(final Long instanceId) {
        final var instance = (HikariDataSource) dataSourcesInstances.get(instanceId);
        instance.close();
        dataSourcesInstances.remove(instanceId);
        dataSourcesProps.remove(instanceId);
        dataSourceRouting.afterPropertiesSet();
    }

    public void removeAllConnections() {
        dataSourcesInstances.forEach((key, value) -> {
            final var instance = (HikariDataSource) value;
            instance.close();
        });
        dataSourcesInstances.clear();
        dataSourcesProps.clear();
        unload();
    }

    public boolean instanceIsAbsent(final Long instanceId) {
        return !dataSourcesInstances.containsKey(instanceId);
    }

    public void unload() {
        CURRENT_INSTANCE.remove();
    }

}
