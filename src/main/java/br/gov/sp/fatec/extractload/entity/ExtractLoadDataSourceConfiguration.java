package br.gov.sp.fatec.extractload.entity;

import br.gov.sp.fatec.extractload.domain.enums.DatabaseDriverEnum;
import br.gov.sp.fatec.extractload.entity.converter.DatabaseProductConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EL_DATASOURCE_CONFIGURATION", schema = "EXTRACT_LOAD_BATCH")
@SQLDelete(sql = "UPDATE EL_DATASOURCE_CONFIGURATION SET DELETED_INDICATOR = true WHERE DATASOURCE_UID = ?")
@Where(clause = "DELETED_INDICATOR = false")
public class ExtractLoadDataSourceConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_DATASOURCE_CONFIGURATION_ID_GEN")
    @SequenceGenerator(name = "EL_DATASOURCE_CONFIGURATION_ID_GEN", sequenceName = "EL_DATASOURCE_CONFIG_SEQ", allocationSize = 1)
    @Column(name = "DATASOURCE_UID", nullable = false)
    private Long uid;

    @NotNull
    @Size(max = 128)
    @Column(name = "DATABASE_NAME", nullable = false, length = 128)
    private String databaseName;

    @NotNull
    @Size(max = 63)
    @Column(name = "DATABASE_HOST", nullable = false, length = 63)
    private String databaseHost;

    @NotNull
    @Positive
    @Min(0)
    @Max(65535)
    @Column(name = "DATABASE_NUMBER_PORT", nullable = false)
    private int databaseNumberPort;

    @NotNull
    @Size(max = 128)
    @Column(name = "DATABASE_USER_NAME", nullable = false, length = 128)
    private String databaseUserName;

    @NotNull
    @Size(max = 128)
    @Column(name = "DATABASE_PASSWORD", nullable = false, length = 128)
    private String databasePassword;

    @Column(name = "DATABASE_PRODUCT_NAME")
    @Convert(converter = DatabaseProductConverter.class)
    private DatabaseDriverEnum databaseProductName;

    @NotNull
    @Column(name = "DELETED_INDICATOR", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private LocalDateTime createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private LocalDateTime updateDateTime;

}