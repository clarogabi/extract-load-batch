package br.gov.sp.fatec.extractload.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "EL_BUNDLED_APP_TABLE", schema = "EXTRACT_LOAD_BATCH")
public class ExtractLoadBundledAppTable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_BUNDLED_APP_TABLE_ID_GEN")
    @SequenceGenerator(name = "EL_BUNDLED_APP_TABLE_ID_GEN", sequenceName = "EL_BUNDLED_APP_TABLE_SEQ", allocationSize = 1)
    @Column(name = "BUNDLE_APP_TABLE_UID", nullable = false)
    private Long uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="DATA_BUNDLE_UID", referencedColumnName ="DATA_BUNDLE_UID", nullable = false)
    private ExtractLoadDataBundle extractLoadDataBundle;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_APP_TABLE_UID", nullable = false)
    private ExtractLoadAppTable sourceAppTable;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARGET_APP_TABLE_UID", nullable = false)
    private ExtractLoadAppTable targetAppTable;

    @NotNull
    @Column(name = "RELATIONAL_ORDERING_NUMBER", nullable = false)
    private Long relationalOrderingNumber;

    @Size(max = 4000)
    @Column(name = "EXTRACT_CUSTOM_QUERY", nullable = false, length = 4000)
    private String extractCustomQuery;

    @Size(max = 4000)
    @Column(name = "LOAD_CUSTOM_INSERT_QUERY", nullable = false, length = 4000)
    private String loadCustomInsertQuery;

    @Size(max = 4000)
    @Column(name = "LOAD_CUSTOM_UPDATE_QUERY", nullable = false, length = 4000)
    private String loadCustomUpdateQuery;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private Timestamp createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private Timestamp updateDateTime;

}