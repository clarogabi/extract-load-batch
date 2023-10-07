package br.gov.sp.fatec.extractload.config.datasource;

import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class TargetDataSourceConfiguration {

    @Bean(name = "targetDataSourceConfig")
    @ConfigurationProperties(prefix = "db.target.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }


    @Bean("targetDataSource")
    public HikariDataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    @Bean("targetJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("targetDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("targetJdbcUtils")
    public JdbcUtils jdbcUtils(@Qualifier("targetDataSource") DataSource dataSource) throws SQLException {
        return new JdbcUtils(dataSource);
    }

    @Bean("targetNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate targetNamedParameterJdbcTemplate(@Qualifier("targetDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
