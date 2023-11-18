package br.gov.sp.fatec.extractload.config.datasource;

import br.gov.sp.fatec.extractload.config.component.PrimaryDataSourceProps;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.PropertyResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static java.util.Objects.nonNull;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "primaryEntityManagerFactory",
    transactionManagerRef = "primaryTransactionManager",
    basePackages = "br.gov.sp.fatec.extractload.repository"
)
@EnableTransactionManagement
public class PrimaryDataSourceConfig {

    private static HikariConfig extractLoadDataSourceConfig(final PrimaryDataSourceProps primaryDataSourceProps) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(primaryDataSourceProps.jdbcUrl());
        config.setUsername(primaryDataSourceProps.username());
        config.setPassword(primaryDataSourceProps.password());
        config.setMinimumIdle(primaryDataSourceProps.minimumIdle());
        config.setMaximumPoolSize(primaryDataSourceProps.maximumPoolSize());
        config.setPoolName(primaryDataSourceProps.poolName());
        config.setConnectionTimeout(primaryDataSourceProps.connectionTimeout());
        config.setIdleTimeout(primaryDataSourceProps.idleTimeout());
        config.setMaxLifetime(primaryDataSourceProps.maxLifetime());
        return config;
    }

    @Primary
    @Bean(name = "extractLoadDataSource")
    public DataSource extractLoadDataSource(final PrimaryDataSourceProps primaryDataSourceProps) {
        return new HikariDataSource(extractLoadDataSourceConfig(primaryDataSourceProps));
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(final EntityManagerFactoryBuilder builder,
        final @Qualifier("extractLoadDataSource") DataSource dataSource) {
        return builder
            .dataSource(dataSource)
            .persistenceUnit("extractLoadDataSource")
            .packages("br.gov.sp.fatec.extractload.entity")
            .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(final @Qualifier("primaryEntityManagerFactory") EntityManagerFactory emf,
        final PropertyResolver propertyResolver) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setJpaDialect(new HibernateJpaDialect());

        var timeout = propertyResolver.getProperty("platform.transaction.manager.default-timeout", Integer.class);
        if (nonNull(timeout)) {
            transactionManager.setDefaultTimeout(timeout);
        }

        return transactionManager;
    }

}
