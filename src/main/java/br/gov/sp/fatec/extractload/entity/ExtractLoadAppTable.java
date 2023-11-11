package br.gov.sp.fatec.extractload.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "EL_APP_TABLE", schema = "EXTRACT_LOAD_BATCH")
public class ExtractLoadAppTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_APP_TABLE_ID_GEN")
    @SequenceGenerator(name = "EL_APP_TABLE_ID_GEN", sequenceName = "EL_APP_TABLE_SEQ", allocationSize = 1)
    @Column(name = "APP_TABLE_UID", nullable = false)
    private Long uid;

    @Size(max = 255)
    @NotNull
    @Column(name = "APP_TABLE_PHYSICAL_NAME", nullable = false)
    private String appTablePhysicalName;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private Timestamp createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private Timestamp updateDateTime;

}