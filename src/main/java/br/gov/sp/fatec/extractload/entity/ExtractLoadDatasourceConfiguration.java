package br.gov.sp.fatec.extractload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "EL_DATASOURCE_CONFIGURATION", schema = "EXTRACT_LOAD_BATCH")
public class ExtractLoadDatasourceConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_DATASOURCE_CONFIGURATION_ID_GEN")
    @SequenceGenerator(name = "EL_DATASOURCE_CONFIGURATION_ID_GEN", sequenceName = "EL_DATASOURCE_CONFIG_SEQ", allocationSize = 1)
    @Column(name = "DATASOURCE_CONFIGURATION_UID", nullable = false)
    private Long uid;

    @Size(max = 50)
    @NotNull
    @Column(name = "DATABASE_NAME", nullable = false, length = 50)
    private String databaseName;

    @Size(max = 50)
    @NotNull
    @Column(name = "DATABASE_PROVIDER", nullable = false, length = 50)
    private String databaseProvider;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_PLATFORM", nullable = false)
    private String databasePlatform;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_CONNECTION_URL", nullable = false)
    private String databaseConnectionUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_USER_NAME", nullable = false)
    private String databaseUserName;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_PASSWORD", nullable = false)
    private String databasePassword;

    @Size(max = 255)
    @NotNull
    @Column(name = "DRIVER_CLASS_NAME", nullable = false)
    private String driverClassName;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_DIALECT", nullable = false)
    private String databaseDialect;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATABASE_SCHEMA", nullable = false)
    private String databaseSchema;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private Timestamp createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private Timestamp updateDateTime;

}