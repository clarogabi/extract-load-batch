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
public class UpdateJdbcItemWriter extends JdbcBatchItemWriter<RowMappedDto> {

    private final String tableName;

    public UpdateJdbcItemWriter(final DataSource dataSource, final String tableName) throws SQLException {
        this.tableName = tableName;
        log.info("Preparing update item writer of table [{}]", tableName);
        final var jdbcUtils = new JdbcUtils(dataSource);
        final var sqlUpdate = jdbcUtils.generateUpdate(tableName);

        super.setDataSource(dataSource);
        super.setItemSqlParameterSourceProvider(new RowMapItemSqlParameterSourceProvider());
        super.setSql(sqlUpdate);
        log.info("Writer SQL Update Query [{}]", sqlUpdate);
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
