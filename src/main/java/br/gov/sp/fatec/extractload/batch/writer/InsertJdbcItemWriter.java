package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InsertJdbcItemWriter extends JdbcBatchItemWriter<RowMappedDto> {

    private final String tableName;

    public InsertJdbcItemWriter(DataSource dataSource, JdbcUtils jdbcUtils, String tableName) {
        this.tableName = tableName;
        log.info("Preparing insert item writer of table [{}]", tableName);
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new RowMapItemSqlParameterSourceProvider());
        var sqlInsert = jdbcUtils.generateInsert(tableName);
        setSql(sqlInsert);
        log.info("Writer SQL Insert Query [{}]", sqlInsert);
    }

    @Override
    public void write(final Chunk<? extends RowMappedDto> chunk) {
        try {
            super.write(chunk);
        } catch (Exception e) {
            throw new RuntimeException(tableName, e);
        }
    }

}
