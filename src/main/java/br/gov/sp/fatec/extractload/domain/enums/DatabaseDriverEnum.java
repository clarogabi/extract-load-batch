package br.gov.sp.fatec.extractload.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

import static java.util.Arrays.stream;

@Getter
@AllArgsConstructor
public enum DatabaseDriverEnum {

    ORACLE("Oracle", "oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@%s:%s:%s"),
    MYSQL("MySQL", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s"),
    SQLSERVER("Microsoft SQL Server", "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        "jdbc:sqlserver://%s:%s;databaseName=%s;integratedSecurity=false;encrypt=false;trustServerCertificate=true;"),
    POSTGRESQL("PostgreSQL", "org.postgresql.Driver", "jdbc:postgresql://%s:%s/%s"),
    DB2("DB2", "com.ibm.db2.jcc.DB2Driver", "jdbc:db2://%s:%s/%s");

    private final String productName;
    private final String className;
    private final String jdbcUrlFormat;

    public static DatabaseDriverEnum fromProductName(final String productName) {
        return stream(values()).filter(it -> Objects.equals(it.productName, productName))
            .findFirst()
            .orElse(null);
    }

}
