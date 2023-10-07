package br.gov.sp.fatec.extractload.batch.reader;

import br.gov.sp.fatec.extractload.api.model.ExtractionTypeEnum;
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
import java.util.*;
import java.util.stream.Collectors;

import static br.gov.sp.fatec.extractload.utils.Constants.ITEM_READER_NAME;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getPrimaryKey;
import static br.gov.sp.fatec.extractload.utils.ExtractLoadUtils.getTableName;

@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExtractJdbcPagingItemReader extends CompositeJdbcPagingItemReader<RowMappedDto> {

    private Set<String> primaryKeys;

    public ExtractJdbcPagingItemReader(DataSource dataSource, JdbcUtils jdbcUtils, NamedParameterJdbcTemplate jdbcTemplate,
                                       Integer fetchSize, BundledAppTableDto bundledAppTableDto, ExtractionTypeEnum extractionType) throws Exception {

        String sourceTableName = bundledAppTableDto.getSourceAppTableName();
        this.primaryKeys = jdbcUtils.getPrimaryKeys(getTableName(sourceTableName));

        log.info("Preparing paging reader of table [{}] with fetch size [{}] and page size [{}]", sourceTableName, fetchSize, fetchSize);

        setName(ITEM_READER_NAME.concat(sourceTableName.toUpperCase()));
        setDataSource(dataSource);
        setPageSize(fetchSize);
        setFetchSize(fetchSize);
        setQueryProvider(getQueryProvider(extractionType, dataSource, sourceTableName, bundledAppTableDto.getExtractCustomQuery().toUpperCase()));
        setRowMapper(new ResultSetRowMapper(jdbcUtils.getPrimaryKeys(getTableName(sourceTableName))));
        setPageProcessor(new ExtractPageProcessor(sourceTableName, getPrimaryKey(primaryKeys), jdbcTemplate));

    }

    protected PagingQueryProvider getQueryProvider(ExtractionTypeEnum extractionType, DataSource dataSource, String tableName, String customQuery) throws Exception {

        Map<String, Order> sortKeys = new LinkedHashMap<>();
        SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
        factory.setDataSource(dataSource);
//        factory.setDatabaseType(dataSource.getConnection().getCatalog()); //TODO

        if (ExtractionTypeEnum.DEFAULT.equals(extractionType)) {
            factory.setSelectClause("*");
            factory.setFromClause(tableName);
            primaryKeys.forEach(p -> sortKeys.put(p, Order.ASCENDING));

        } else {
            factory.setSelectClause((String) customQuery.subSequence(customQuery.indexOf("SELECT"), customQuery.indexOf("FROM")));
            String fromClause;
            if (customQuery.contains("WHERE")) {
                fromClause = (String) customQuery.subSequence(customQuery.indexOf("FROM"), customQuery.indexOf("WHERE"));
                String whereClause = (String) customQuery.subSequence(customQuery.toUpperCase().indexOf("WHERE"), customQuery.toUpperCase().indexOf("ORDER BY"));
                factory.setWhereClause(whereClause);
            } else {
                fromClause = (String) customQuery.subSequence(customQuery.indexOf("FROM"), customQuery.indexOf("ORDER BY"));
            }
            factory.setFromClause(fromClause);
            String orderByClause = (String) customQuery.subSequence(customQuery.toUpperCase().indexOf("ORDER BY") + 8, customQuery.toUpperCase().indexOf(";"));
            List<String> orderKeys;

            if (orderByClause.contains("ASC")) {
                orderKeys = Arrays.stream(orderByClause.subSequence(0, orderByClause.length() - 3).toString().split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
                orderKeys.forEach(k -> sortKeys.put(k, Order.ASCENDING));
            } else if (customQuery.contains("DESC")) {
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
