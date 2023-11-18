package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.generateSelectIdsQuery;
import static java.util.Objects.nonNull;

public class ExtractPageProcessor implements PageProcessor<RowMappedDto> {

    private final DataSource dataSource;
    private final String tableName;
    private final String primaryKeyName;

    public ExtractPageProcessor(final String tableName, final String primaryKeyName, final DataSource dataSource) {
        this.dataSource = dataSource;
        this.tableName = tableName;
        this.primaryKeyName = primaryKeyName;
    }

    @Override
    public void process(List<RowMappedDto> page) {

        if (!page.isEmpty()) {
            MapSqlParameterSource parameters = new MapSqlParameterSource();
            parameters.addValue("ids", page.stream()
                .map(row -> row.getRow().entrySet()
                    .stream()
                    .filter(field -> field.getKey().primaryKey() && primaryKeyName.equals(field.getKey().name()))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElse(null))
                .toList());

            var jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
            List<String> ids = jdbcTemplate.query(generateSelectIdsQuery(tableName, primaryKeyName), parameters, new RowExtractor());

            if (nonNull(ids) && !ids.isEmpty()) {
                page.forEach(row -> row.getRow()
                    .entrySet().stream()
                    .filter(map -> map.getKey().primaryKey())
                    .findFirst()
                    .ifPresent(field -> {
                        if (ids.contains(field.getValue().toString())) {
                            row.setLoadMode(LoadModeEnum.UPDATE);
                        }
                    }));
            }
        }
    }

    private class RowExtractor implements ResultSetExtractor<List<String>> {

        private final List<String> ids;

        public RowExtractor() {
            this.ids = new ArrayList<>();
        }

        @Override
        public List<String> extractData(final ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {
                ids.add(rs.getString(primaryKeyName));
            }
            return List.copyOf(ids);
        }

    }

}
