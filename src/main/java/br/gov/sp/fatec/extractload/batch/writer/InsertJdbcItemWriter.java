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
public class InsertJdbcItemWriter extends JdbcBatchItemWriter<RowMappedDto> {

    private String sqlInsert;
    private String tableName;

    public InsertJdbcItemWriter(DataSource dataSource, JdbcUtils jdbcUtils, String tableName) {
        this.tableName = tableName;

        log.info("Preparing insert item writer of table [{}]", tableName);
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new RowMapItemSqlParameterSourceProvider());
        sqlInsert = jdbcUtils.generateInsert(tableName);
        log.info("Writer SQL Insert Query [{}]", sqlInsert);
        setSql(sqlInsert);
    }

    @Override
    public void write(List<? extends RowMappedDto> items) {
        try {
            super.write(items);
        } catch (Exception e) {
            throw new RuntimeException(tableName, e);
        }
    }

}
