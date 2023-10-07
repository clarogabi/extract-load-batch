package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UpdateJdbcItemWriter extends JdbcBatchItemWriter<RowMappedDto> {

    private String sqlUpdate;
    private String tableName;
    private JdbcUtils jdbcUtils;

    public UpdateJdbcItemWriter(DataSource dataSource, JdbcUtils jdbcUtils, String tableName) {

        this.tableName = tableName;
        this.jdbcUtils = jdbcUtils;

        log.info("Preparing update item writer of table [{}]", tableName);
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new RowMapItemSqlParameterSourceProvider());
        sqlUpdate = jdbcUtils.generateUpdate(tableName);
        setSql(sqlUpdate);
    }

    @Override
    public void write(List<? extends RowMappedDto> items) {
        try {
            super.write(items);
        } catch (Exception e) {
//            throw new DataMigrationWriterException(tableName, e);
            throw new RuntimeException(tableName, e);
        }
    }

}
