package br.gov.sp.fatec.extractload.config.component;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource.hikari")
public record PrimaryDataSourceProps(String jdbcUrl, String username, String password, String poolName, int minimumIdle,
                                     int maximumPoolSize, Long idleTimeout, Long maxLifetime, Long connectionTimeout,
                                     Long leakDetectionThreshold, Long validationTimeout) {
}
