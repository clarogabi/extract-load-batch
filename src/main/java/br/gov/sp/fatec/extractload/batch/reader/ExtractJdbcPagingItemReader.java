package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static br.gov.sp.fatec.extractload.utils.Constants.ASC;
import static br.gov.sp.fatec.extractload.utils.Constants.ASTERISK;
import static br.gov.sp.fatec.extractload.utils.Constants.COMMA;
import static br.gov.sp.fatec.extractload.utils.Constants.DESC;
import static br.gov.sp.fatec.extractload.utils.Constants.FROM;
import static br.gov.sp.fatec.extractload.utils.Constants.ITEM_READER_NAME;
import static br.gov.sp.fatec.extractload.utils.Constants.ORDER_BY;
import static br.gov.sp.fatec.extractload.utils.Constants.SELECT;
import static br.gov.sp.fatec.extractload.utils.Constants.SEMICOLON;
import static br.gov.sp.fatec.extractload.utils.Constants.WHERE;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getPrimaryKey;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getTableName;
import static java.util.Objects.isNull;

@Slf4j
@Configuration
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExtractJdbcPagingItemReader extends CompositeJdbcPagingItemReader<RowMappedDto> {

    private final Set<String> primaryKeys;

    public ExtractJdbcPagingItemReader(DataSource dataSource, Integer fetchSize, BundledAppTableDto table) throws Exception {
        var jdbcUtils = new JdbcUtils(dataSource);
        var sourceTableName = getTableName(table.sourceAppTableName());
        this.primaryKeys = jdbcUtils.getPrimaryKeys(sourceTableName);

        log.info("Preparing paging reader of table [{}] with fetch size [{}] and page size [{}]", sourceTableName, fetchSize, fetchSize);
        var queryProvider = getQueryProvider(dataSource, sourceTableName, table.extractCustomQuery());
        setName(ITEM_READER_NAME.concat(sourceTableName.toUpperCase()));
        setDataSource(dataSource);
        setPageSize(fetchSize);
        setFetchSize(fetchSize);
        setQueryProvider(queryProvider);
        setRowMapper(new ResultSetRowMapper(jdbcUtils.getPrimaryKeys(sourceTableName)));
        setPageProcessor(new ExtractPageProcessor(sourceTableName, getPrimaryKey(primaryKeys), dataSource));
        log.info("Reader SQL Query [{}]", queryProvider.generateFirstPageQuery(fetchSize));
    }

    private PagingQueryProvider getQueryProvider(DataSource dataSource, String tableName, String customQuery)
        throws Exception {

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
            var query = customQuery.toUpperCase();

            factory.setSelectClause(query.subSequence(query.indexOf(SELECT), query.indexOf(FROM)).toString());

            String fromClause;
            if (query.contains(WHERE)) {
                fromClause = query.subSequence(query.indexOf(FROM), query.indexOf(WHERE)).toString();
                var whereClause = query.subSequence(query.toUpperCase().indexOf(WHERE), query.toUpperCase().indexOf(ORDER_BY)).toString();
                factory.setWhereClause(whereClause);
            } else {
                fromClause = query.subSequence(query.indexOf(FROM), query.indexOf(ORDER_BY)).toString();
            }
            factory.setFromClause(fromClause);

            String orderByClause = query.subSequence(query.toUpperCase().indexOf(ORDER_BY) + 8, query.toUpperCase().indexOf(SEMICOLON)).toString();
            List<String> orderKeys;
            if (orderByClause.contains(ASC)) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - 3).toString().split(COMMA))
                    .map(String::trim)
                    .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            } else if (query.contains(DESC)) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - 4).toString().split(COMMA))
                    .map(String::trim)
                    .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.DESCENDING));
            } else {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length()).toString().split(COMMA))
                    .map(String::trim)
                    .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            }
        }

        factory.setSortKeys(sortKeys);
        return factory.getObject();
    }

}
