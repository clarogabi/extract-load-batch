package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static br.gov.sp.fatec.extractload.utils.Constants.ASC;
import static br.gov.sp.fatec.extractload.utils.Constants.ASTERISK;
import static br.gov.sp.fatec.extractload.utils.Constants.COMMA;
import static br.gov.sp.fatec.extractload.utils.Constants.DESC;
import static br.gov.sp.fatec.extractload.utils.Constants.EIGHT;
import static br.gov.sp.fatec.extractload.utils.Constants.FOUR;
import static br.gov.sp.fatec.extractload.utils.Constants.FROM;
import static br.gov.sp.fatec.extractload.utils.Constants.ITEM_READER_NAME;
import static br.gov.sp.fatec.extractload.utils.Constants.ORDER_BY;
import static br.gov.sp.fatec.extractload.utils.Constants.SELECT;
import static br.gov.sp.fatec.extractload.utils.Constants.SEMICOLON;
import static br.gov.sp.fatec.extractload.utils.Constants.THREE;
import static br.gov.sp.fatec.extractload.utils.Constants.WHERE;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getPrimaryKey;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getTableName;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.toUpperCase;
import static java.util.Objects.isNull;

@Slf4j
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExtractJdbcPagingItemReader extends CompositeJdbcPagingItemReader<RowMappedDto> {

    private final Set<String> primaryKeys;

    @SneakyThrows(SQLException.class)
    public ExtractJdbcPagingItemReader(final DataSource dataSource, final int pageSize, final int fetchSize, final BundledAppTableDto table) {
        final var jdbcUtils = new JdbcUtils(dataSource);
        final var sourceTableName = getTableName(table.sourceAppTableName());
        this.primaryKeys = jdbcUtils.getPrimaryKeys(sourceTableName);

        log.info("Preparing paging reader of table [{}] with fetch size [{}] and page size [{}]", sourceTableName, fetchSize, fetchSize);
        final var queryProvider = buildQueryProvider(dataSource, sourceTableName, table.extractCustomQuery());
        super.setName(ITEM_READER_NAME.concat(toUpperCase(sourceTableName)));
        super.setDataSource(dataSource);
        super.setPageSize(pageSize);
        super.setFetchSize(fetchSize);
        super.setQueryProvider(queryProvider);
        super.setRowMapper(new ResultSetRowMapper(jdbcUtils.getPrimaryKeys(sourceTableName)));
        super.setPageProcessor(new ExtractPageProcessor(sourceTableName, getPrimaryKey(primaryKeys), dataSource));
        log.info("Reader SQL Query [{}]", queryProvider.generateFirstPageQuery(fetchSize));
    }

    @SneakyThrows
    private PagingQueryProvider buildQueryProvider(final DataSource dataSource, final String tableName, final String customQuery) {

        Map<String, Order> sortKeys = new LinkedHashMap<>();
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);

        if (isNull(customQuery) || customQuery.isBlank()) {
            log.info("Bundled table [{}] has non custom query, generating default paging query sql.", tableName);
            factory.setSelectClause(ASTERISK);
            factory.setFromClause(tableName);
            primaryKeys.forEach(p -> sortKeys.put(p, Order.ASCENDING));
        } else {
            log.info("Bundled table [{}] has custom query, generating paging query sql from it.", tableName);
            var query = toUpperCase(customQuery);

            factory.setSelectClause(query.subSequence(query.indexOf(SELECT), query.indexOf(FROM)).toString());

            String fromClause;
            if (query.contains(WHERE)) {
                fromClause = query.subSequence(query.indexOf(FROM), query.indexOf(WHERE)).toString();
                var whereClause = query.subSequence(toUpperCase(query).indexOf(WHERE), toUpperCase(query).indexOf(ORDER_BY)).toString();
                factory.setWhereClause(whereClause);
            } else {
                fromClause = query.subSequence(query.indexOf(FROM), query.indexOf(ORDER_BY)).toString();
            }
            factory.setFromClause(fromClause);

            String orderByClause = query.subSequence(toUpperCase(query).indexOf(ORDER_BY) + EIGHT, toUpperCase(query).indexOf(SEMICOLON)).toString();
            List<String> orderKeys;
            if (orderByClause.contains(ASC)) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - THREE).toString().split(COMMA))
                    .map(String::trim)
                    .toList();
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            } else if (query.contains(DESC)) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - FOUR).toString().split(COMMA))
                    .map(String::trim)
                    .toList();
                orderKeys.forEach(k -> sortKeys.put(k, Order.DESCENDING));
            } else {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length()).toString().split(COMMA))
                    .map(String::trim)
                    .toList();
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            }
        }

        factory.setSortKeys(sortKeys);
        return factory.getObject();
    }

}
