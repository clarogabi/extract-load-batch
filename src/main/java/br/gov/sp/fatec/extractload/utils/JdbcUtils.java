package br.gov.sp.fatec.extractload.utils;

import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static br.gov.sp.fatec.extractload.utils.Constants.AND;
import static br.gov.sp.fatec.extractload.utils.Constants.ATTRIBUTION;
import static br.gov.sp.fatec.extractload.utils.Constants.COLON;
import static br.gov.sp.fatec.extractload.utils.Constants.COLUMN_NAME;
import static br.gov.sp.fatec.extractload.utils.Constants.COMMA;
import static br.gov.sp.fatec.extractload.utils.Constants.BLANK_SPACE;
import static br.gov.sp.fatec.extractload.utils.Constants.INSERT_INTO;
import static br.gov.sp.fatec.extractload.utils.Constants.LEFT_PARENTHESES;
import static br.gov.sp.fatec.extractload.utils.Constants.RIGHT_PARENTHESES;
import static br.gov.sp.fatec.extractload.utils.Constants.SET;
import static br.gov.sp.fatec.extractload.utils.Constants.UPDATE;
import static br.gov.sp.fatec.extractload.utils.Constants.VALUES;
import static br.gov.sp.fatec.extractload.utils.Constants.WHERE;

@Slf4j
public class JdbcUtils {

    private Connection connection;

    public JdbcUtils(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Set<String> getPrimaryKeys(final String tableName) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();

        ResultSet pk = connection.getMetaData().getPrimaryKeys(null, null, tableName);
        while (pk.next()) {
            primaryKeys.add(pk.getString(COLUMN_NAME));
        }

        return primaryKeys;
    }

    private Set<String> getFieldsTable(final String tableName) throws SQLException {
        Set<String> fields = new HashSet<>();

        ResultSet columns = connection.getMetaData().getColumns(null, null, tableName, null);
        while (columns.next()) {
            String fieldName = columns.getString(COLUMN_NAME);
            fields.add(fieldName);
        }

        return fields;
    }

    public String generateInsert(final String tableName) {
        StringBuilder sbSqlInsert = new StringBuilder();
        try {
            var fields = getFieldsTable(tableName);
            StringBuilder sbValuesInsert = new StringBuilder();
            sbSqlInsert.append(INSERT_INTO).append(BLANK_SPACE);
            sbSqlInsert.append(tableName).append(BLANK_SPACE);
            sbSqlInsert.append(LEFT_PARENTHESES);

            for (Iterator<String> itField = fields.iterator(); itField.hasNext(); ) {
                var field = itField.next();
                sbSqlInsert.append(field);
                sbValuesInsert.append(COLON);
                sbValuesInsert.append(field);

                if (itField.hasNext()) {
                    sbSqlInsert.append(COMMA).append(BLANK_SPACE);
                    sbValuesInsert.append(COMMA).append(BLANK_SPACE);
                }
            }

            sbSqlInsert.append(RIGHT_PARENTHESES);
            sbSqlInsert.append(BLANK_SPACE).append(VALUES).append(BLANK_SPACE);
            sbSqlInsert.append(LEFT_PARENTHESES);
            sbSqlInsert.append(sbValuesInsert);
            sbSqlInsert.append(RIGHT_PARENTHESES);

        } catch (SQLException e) {
            log.error("Failed to generate insert query", e);
        }
        return sbSqlInsert.toString();
    }

    public String generateUpdate(final String tableName) {
        StringBuilder sbSqlUpdate = new StringBuilder();

        try {
            var fields = getFieldsTable(tableName);
            var primaryKeys = getPrimaryKeys(tableName);
            fields.removeAll(primaryKeys);

            sbSqlUpdate.append(UPDATE).append(BLANK_SPACE);
            sbSqlUpdate.append(tableName).append(BLANK_SPACE);
            sbSqlUpdate.append(SET).append(BLANK_SPACE);
            setValueOnFields(fields, sbSqlUpdate);
            sbSqlUpdate.append(BLANK_SPACE).append(WHERE).append(BLANK_SPACE);

            for (Iterator<String> itPK = primaryKeys.iterator(); itPK.hasNext();) {
                var pkField = itPK.next();
                sbSqlUpdate.append(pkField).append(BLANK_SPACE).append(ATTRIBUTION);
                sbSqlUpdate.append(BLANK_SPACE).append(COLON).append(pkField);

                if (itPK.hasNext()) {
                    sbSqlUpdate.append(BLANK_SPACE).append(AND).append(BLANK_SPACE);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to generate update query", e);
        }

        return sbSqlUpdate.toString();
    }

    private static void setValueOnFields(Set<String> fields, StringBuilder sbSqlUpdate) {
        for (Iterator<String> itField = fields.iterator(); itField.hasNext();) {
            var field = itField.next();
            sbSqlUpdate.append(field).append(BLANK_SPACE).append(ATTRIBUTION);
            sbSqlUpdate.append(BLANK_SPACE).append(COLON).append(field);

            if (itField.hasNext()) {
                sbSqlUpdate.append(COMMA).append(BLANK_SPACE);
            }
        }
    }

}
