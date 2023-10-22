package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.dto.RowMappedDto;
import br.gov.sp.fatec.extractload.utils.JdbcUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static br.gov.sp.fatec.extractload.utils.Constants.ITEM_READER_NAME;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getPrimaryKey;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getTableName;
import static java.util.Objects.isNull;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExtractJdbcPagingItemReader extends CompositeJdbcPagingItemReader<RowMappedDto> {

    private Set<String> primaryKeys;

    public ExtractJdbcPagingItemReader(DataSource dataSource, JdbcUtils jdbcUtils, NamedParameterJdbcTemplate jdbcTemplate,
                                       Integer fetchSize, BundledAppTableDto bundledAppTableDto) throws Exception {

        var sourceTableName = bundledAppTableDto.getSourceAppTableName();
        this.primaryKeys = jdbcUtils.getPrimaryKeys(getTableName(sourceTableName));

        log.info("Preparing paging reader of table [{}] with fetch size [{}] and page size [{}]", sourceTableName, fetchSize, fetchSize);
        var queryProvider = getQueryProvider(dataSource, sourceTableName, bundledAppTableDto.getExtractCustomQuery());
        setName(ITEM_READER_NAME.concat(sourceTableName.toUpperCase()));
        setDataSource(dataSource);
        setPageSize(fetchSize);
        setFetchSize(fetchSize);
        setQueryProvider(queryProvider);
        setRowMapper(new ResultSetRowMapper(jdbcUtils.getPrimaryKeys(getTableName(sourceTableName))));
        setPageProcessor(new ExtractPageProcessor(sourceTableName, getPrimaryKey(primaryKeys), jdbcTemplate));
        log.info("Reader SQL Query [{}]", queryProvider.generateFirstPageQuery(fetchSize));
    }

    protected PagingQueryProvider getQueryProvider(DataSource dataSource, String tableName, String customQuery)
        throws Exception {

        Map<String, Order> sortKeys = new LinkedHashMap<>();
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
//        factory.setDatabaseType(dataSource.getConnection().getCatalog()); //TODO

        if (isNull(customQuery)) {
            log.info("Bundled table [{}] has non custom query, generating default paging query sql.", tableName);
            factory.setSelectClause("*");
            factory.setFromClause(tableName);
            primaryKeys.forEach(p -> sortKeys.put(p, Order.ASCENDING));
        } else {
            log.info("Bundled table [{}] has custom query, generating paging query sql from it.", tableName);
            var query = customQuery.toUpperCase();
            factory.setSelectClause((String) query.subSequence(query.indexOf("SELECT"), query.indexOf("FROM")));
            String fromClause;
            if (query.contains("WHERE")) {
                fromClause = (String) query.subSequence(query.indexOf("FROM"), query.indexOf("WHERE"));
                String whereClause = (String) query.subSequence(query.toUpperCase().indexOf("WHERE"), query.toUpperCase().indexOf("ORDER BY"));
                factory.setWhereClause(whereClause);
            } else {
                fromClause = (String) query.subSequence(query.indexOf("FROM"), query.indexOf("ORDER BY"));
            }
            factory.setFromClause(fromClause);
            String orderByClause = (String) query.subSequence(query.toUpperCase().indexOf("ORDER BY") + 8, query.toUpperCase().indexOf(";"));
            List<String> orderKeys;

            if (orderByClause.contains("ASC")) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - 3).toString().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            } else if (query.contains("DESC")) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - 4).toString().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.DESCENDING));
            } else {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length()).toString().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            }

        }

        factory.setSortKeys(sortKeys);
        return factory.getObject();
    }

}
