package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.FieldMetadataDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ResultSetRowMapper implements RowMapper<RowMappedDto> {
	
	private final Set<String> primaryKeys;
	
	public ResultSetRowMapper(Set<String> primaryKeys) {
		this.primaryKeys = primaryKeys;
	}
	
	@Override
	public RowMappedDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		final ResultSetMetaData m = rs.getMetaData();
		int columnsCount = m.getColumnCount();
		
		Map<FieldMetadataDto, Object> objMapped = new HashMap<>();
		
		for (int i = 1; i <= columnsCount; i++) {
			int jdbcType = m.getColumnType(i);
			Object value = getObject(rs, i, jdbcType);
			String columnName = m.getColumnName(i);
			FieldMetadataDto keyField = new FieldMetadataDto(i, columnName, jdbcType,
					Objects.nonNull(primaryKeys) && primaryKeys.contains(columnName));
			objMapped.put(keyField, value);
		}

		return RowMappedDto.builder()
				.row(objMapped)
				.loadMode(LoadModeEnum.INSERT)
				.build();
	}
	
	public Object getObject(final ResultSet rs, final int index, final int jdbcType) throws SQLException {
        switch (jdbcType) {
            case Types.DATE:
            case Types.TIMESTAMP:
                return rs.getTimestamp(index);
            case Types.TIME:
                return rs.getTime(index);
            case Types.BLOB:
                return rs.getBlob(index);
            case Types.CLOB:
                return rs.getClob(index);
            default:
            	break;
        }
		return rs.getObject(index);
    }
	
}
