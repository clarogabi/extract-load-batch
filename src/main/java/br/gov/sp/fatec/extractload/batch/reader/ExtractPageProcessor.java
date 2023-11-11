package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.generateSelectIdsQuery;

public class ExtractPageProcessor implements PageProcessor<RowMappedDto> {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final String tableName;
    private final String primaryKeyName;

    public ExtractPageProcessor(String tableName, String primaryKeyName, NamedParameterJdbcTemplate jdbcTemplate) {
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void process(List<RowMappedDto> page) {
        List<String> ids = new ArrayList<>();

        if (!page.isEmpty()) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("ids", page.stream()
                .map(row -> row.getRow().entrySet()
                    .stream()
                    .filter(field -> field.getKey().isPrimaryKey() && primaryKeyName.equals(field.getKey().getName()))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(null)).collect(Collectors.toList()));

            jdbcTemplate.query(generateSelectIdsQuery(tableName, primaryKeyName), parameters, new RowExtractor(ids));

            if (!ids.isEmpty()) {
                page.forEach(row -> row.getRow()
                    .entrySet().stream()
                    .filter(map -> map.getKey().isPrimaryKey())
                    .findFirst()
                    .ifPresent(field -> {
                        if (ids.contains(field.getValue().toString())) {
                            row.setLoadMode(LoadModeEnum.UPDATE);
                        }
                    }));
            }
        }
    }

    private class RowExtractor implements ResultSetExtractor<Void> {

        private final List<String> ids;

        public RowExtractor(List<String> ids) {
            this.ids = ids;
        }

        @Override
        public Void extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
                ids.add(rs.getString(primaryKeyName));
            }
            return null;
        }

    }

}
