package br.gov.sp.fatec.extractload.utils;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class JdbcUtils {

    private Connection connection;

    public JdbcUtils(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Set<String> getPrimaryKeys(String tableName) throws SQLException {
        Set<String> primaryKeys = new HashSet<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        ResultSet pk = databaseMetaData.getPrimaryKeys(null, null, tableName);

        while (pk.next()) {
            primaryKeys.add(pk.getString("COLUMN_NAME"));
        }

        return primaryKeys;
    }

    private Set<String> getFieldsTable(String tableName) throws SQLException {
        Set<String> fields = new HashSet<>();
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);

        while (columns.next()) {
            String fieldName = columns.getString("COLUMN_NAME");
            fields.add(fieldName);
        }

        return fields;
    }

    public String generateInsert(String tableName) {
        StringBuilder sbSqlInsert = new StringBuilder();
        try {
            Set<String> fields = getFieldsTable(tableName);
            StringBuilder sbValuesInsert = new StringBuilder();
            sbSqlInsert.append("INSERT INTO ");
            sbSqlInsert.append(tableName);
            sbSqlInsert.append("(");

            for (Iterator<String> itField = fields.iterator(); itField.hasNext(); ) {
                String field = itField.next();
                sbSqlInsert.append(field);
                sbValuesInsert.append(":");
                sbValuesInsert.append(field);

                if (itField.hasNext()) {
                    sbSqlInsert.append(",");
                    sbValuesInsert.append(",");
                }
            }

            sbSqlInsert.append(")");
            sbSqlInsert.append("VALUES ");
            sbSqlInsert.append("(");
            sbSqlInsert.append(sbValuesInsert);
            sbSqlInsert.append(")");


        } catch (SQLException e) {
            log.error("Failed to generate insert query", e);
        }
        return sbSqlInsert.toString();
    }

    public String generateUpdate(String tableName) {

        StringBuilder sbSqlUpdate = new StringBuilder();

        try {
            Set<String> fields = getFieldsTable(tableName);
            Set<String> primaryKeys = getPrimaryKeys(tableName);
            fields.removeAll(primaryKeys);

            sbSqlUpdate.append("UPDATE ");
            sbSqlUpdate.append(tableName);
            sbSqlUpdate.append(" SET ");
            setValueOnFields(fields, sbSqlUpdate);
            sbSqlUpdate.append(" WHERE ");

            for (Iterator<String> itPK = primaryKeys.iterator(); itPK.hasNext(); ) {
                String pkField = itPK.next();
                sbSqlUpdate.append(pkField);
                sbSqlUpdate.append(" = :");
                sbSqlUpdate.append(pkField);

                if (itPK.hasNext()) {
                    sbSqlUpdate.append(" AND ");
                }
            }
        } catch (SQLException e) {
            log.error("Failed to generate update query", e);
        }

        return sbSqlUpdate.toString();
    }

    private void setValueOnFields(Set<String> fields, StringBuilder sbSqlUpdate) {

        for (Iterator<String> itField = fields.iterator(); itField.hasNext();) {
            String field = itField.next();
            sbSqlUpdate.append(field);
            sbSqlUpdate.append(" = :");
            sbSqlUpdate.append(field);

            if (itField.hasNext()) {
                sbSqlUpdate.append(",");
            }
        }
    }

}
