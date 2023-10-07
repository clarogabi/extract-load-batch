package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DatasourceDto {

    private Long uid;
    private String databaseName;
    private String databaseProvider;
    private String databasePlatform;
    private String databaseConnectionUrl;
    private String databaseUserName;
    private String databasePassword;
    private String driverClassName;
    private String databaseDialect;
    private String databaseSchema;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

}
