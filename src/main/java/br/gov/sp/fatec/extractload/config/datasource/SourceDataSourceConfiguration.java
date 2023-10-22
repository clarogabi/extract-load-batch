package br.gov.sp.fatec.extractload.config.datasource;

import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class SourceDataSourceConfiguration {

    @Bean(name = "sourceDataSourceConfig")
    @ConfigurationProperties(prefix = "db.source.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean("sourceDataSource")
    public HikariDataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean("sourceJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("sourceDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("sourceJdbcUtils")
    public JdbcUtils jdbcUtils(@Qualifier("sourceDataSource") DataSource dataSource) throws SQLException {
        return new JdbcUtils(dataSource);
    }

}
