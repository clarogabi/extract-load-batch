package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.FieldMetadataDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.nonNull;

public class ResultSetRowMapper implements RowMapper<RowMappedDto> {

    private final Set<String> primaryKeys;

    public ResultSetRowMapper(final Set<String> primaryKeys) {
        this.primaryKeys = Set.copyOf(primaryKeys);
    }

    @Override
    public RowMappedDto mapRow(final ResultSet rs, int rowNumber) throws SQLException {
        final var metaData = rs.getMetaData();
        final var columnsCount = metaData.getColumnCount();

        Map<FieldMetadataDto, Object> objMapped = new HashMap<>();

        for (int i = 1; i <= columnsCount; i++) {
            final int jdbcType = metaData.getColumnType(i);
            final var value = getObject(rs, i, jdbcType);
            final var columnName = metaData.getColumnName(i);
            final var keyField = new FieldMetadataDto(i, columnName, jdbcType, nonNull(primaryKeys) && primaryKeys.contains(columnName));
            objMapped.put(keyField, value);
        }

        return new RowMappedDto(objMapped, LoadModeEnum.INSERT);
    }

    public Object getObject(final ResultSet rs, final int index, final int jdbcType) throws SQLException {
        return switch (jdbcType) {
            case Types.DATE, Types.TIMESTAMP -> rs.getTimestamp(index);
            case Types.TIME -> rs.getTime(index);
            case Types.BLOB -> rs.getBlob(index);
            case Types.CLOB -> rs.getClob(index);
            default -> rs.getObject(index);
        };
    }

}
