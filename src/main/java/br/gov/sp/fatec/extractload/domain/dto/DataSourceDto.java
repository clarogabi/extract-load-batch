package br.gov.sp.fatec.extractload.domain.dto;

import br.gov.sp.fatec.extractload.domain.enums.DatabaseDriverEnum;

import java.time.LocalDateTime;

public record DataSourceDto(Long uid,
                            DatabaseDriverEnum databaseDriver,
                            String hostName,
                            Integer numberPort,
                            String databaseName,
                            String databaseSchema,
                            String userName,
                            String password,
                            LocalDateTime createDateTime,
                            LocalDateTime updateDateTime) {

    public String getJdbcUrl() {
        return String.format(databaseDriver.getJdbcUrlFormat(), hostName, numberPort, databaseName);
    }

}
