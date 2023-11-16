package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.exception.UnprocessableEntityProblem;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class InsertJdbcItemWriter extends JdbcBatchItemWriter<RowMappedDto> {

    private final String tableName;

    public InsertJdbcItemWriter(DataSource dataSource, String tableName) throws SQLException {
        this.tableName = tableName;
        log.info("Preparing insert item writer of table [{}]", tableName);
        setDataSource(dataSource);
        setItemSqlParameterSourceProvider(new RowMapItemSqlParameterSourceProvider());
        var jdbcUtils = new JdbcUtils(dataSource);
        var sqlInsert = jdbcUtils.generateInsert(tableName);
        setSql(sqlInsert);
        log.info("Writer SQL Insert Query [{}]", sqlInsert);
    }

    @Override
    public void write(final Chunk<? extends RowMappedDto> chunk) {
        try {
            super.write(chunk);
        } catch (Exception e) {
            log.error("Error writing chunk of table [{}]", tableName, e);
            throw new UnprocessableEntityProblem(String.format("Ocorreu um erro ao carregar os registros na tabela de destino: [%s]", tableName));
        }
    }

}
