package br.gov.sp.fatec.extractload.config.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "extractLoadDataSourceEntityManagerFactory",
        transactionManagerRef = "extractLoadDataSourceTransactionManager",
        basePackages = "br.gov.sp.fatec.extractload.repository"
)
@EnableTransactionManagement
public class ExtractLoadDataSourceConfiguration {

    @Autowired
    protected Environment environment;

    @Bean(name = "extractLoadDataSourceConfig")
    @ConfigurationProperties(prefix = "extract-load-batch.spring.datasource.hikari")
    public HikariConfig extractLoadDataSourceConfig() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name = "extractLoadDataSource")
    public HikariDataSource extractLoadDataSource() {
        return new HikariDataSource(extractLoadDataSourceConfig());
    }

    @Primary
    @Bean(name = "extractLoadDataSourceEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean extractLoadDataSourceEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("extractLoadDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .persistenceUnit("extractLoadDataSource")
                .packages("br.gov.sp.fatec.*.entity")
                .build();
    }

    @Primary
    @Bean(name = "extractLoadDataSourceTransactionManager")
    public PlatformTransactionManager extractLoadDataSourceTransactionManager(
            @Autowired @Qualifier("extractLoadDataSourceEntityManagerFactory") EntityManagerFactory emf) {

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setJpaDialect(getJpaDialect());

        Integer timeout = environment.getProperty("jpa.custom.transaction.timeout", Integer.class);
        if (timeout != null) {
            transactionManager.setDefaultTimeout(timeout);
        }

        return transactionManager;
    }

    @Bean
    public JpaDialect getJpaDialect() {
        return new HibernateJpaDialect();
    }
}
