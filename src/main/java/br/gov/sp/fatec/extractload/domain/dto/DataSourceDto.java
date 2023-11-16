package br.gov.sp.fatec.extractload.domain.dto;

import br.gov.sp.fatec.extractload.domain.enums.DatabaseDriverEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DataSourceDto {

    private Long uid;
    private DatabaseDriverEnum databaseDriver;
    private String hostName;
    private Integer numberPort;
    private String databaseName;
    private String userName;
    private String password;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public String getJdbcUrl() {
        return String.format(databaseDriver.getJdbcUrlFormat(), hostName, numberPort, databaseName);
    }

}
