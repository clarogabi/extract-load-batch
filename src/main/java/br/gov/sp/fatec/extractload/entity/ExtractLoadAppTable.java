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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EL_APP_TABLE", schema = "EXTRACT_LOAD_BATCH")
public class ExtractLoadAppTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_APP_TABLE_ID_GEN")
    @SequenceGenerator(name = "EL_APP_TABLE_ID_GEN", sequenceName = "EL_APP_TABLE_SEQ", allocationSize = 1)
    @Column(name = "APP_TABLE_UID", nullable = false)
    private Long uid;

    @NotNull
    @Size(max = 128)
    @Column(name = "APP_TABLE_PHYSICAL_NAME", nullable = false)
    private String appTablePhysicalName;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private LocalDateTime createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private LocalDateTime updateDateTime;

}