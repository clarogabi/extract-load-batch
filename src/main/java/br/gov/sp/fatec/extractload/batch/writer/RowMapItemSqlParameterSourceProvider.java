package br.gov.sp.fatec.extractload.batch.writer;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@Slf4j
public class RowMapItemSqlParameterSourceProvider implements ItemSqlParameterSourceProvider<RowMappedDto> {

    @Override
    public SqlParameterSource createSqlParameterSource(RowMappedDto item) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        item.getRow().forEach((keyField, value) ->
                mapSqlParameterSource.addValue(keyField.getName(), value, keyField.getJdbcType())
        );
        return mapSqlParameterSource;
    }
}
