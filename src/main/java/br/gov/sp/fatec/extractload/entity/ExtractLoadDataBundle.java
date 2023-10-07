package br.gov.sp.fatec.extractload.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "EL_DATA_BUNDLE", schema = "EXTRACTLOADBATCH")
public class ExtractLoadDataBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EL_DATA_BUNDLE_ID_GEN")
    @SequenceGenerator(name = "EL_DATA_BUNDLE_ID_GEN", sequenceName = "EL_DATA_BUNDLE_SEQ", allocationSize = 1)
    @Column(name = "DATA_BUNDLE_UID", nullable = false)
    private Long uid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SOURCE_DATASOURCE_CONFIG_UID", nullable = false)
    private ExtractLoadDatasourceConfiguration sourceDatasourceConfig;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TARGET_DATASOURCE_CONFIG_UID", nullable = false)
    private ExtractLoadDatasourceConfiguration targetDatasourceConfig;

    @Size(max = 255)
    @NotNull
    @Column(name = "DATA_BUNDLE_NAME", nullable = false)
    private String dataBundleName;

    @NotNull
    @Column(name = "CREATE_DATE_TIME", nullable = false)
    private Timestamp createDateTime;

    @NotNull
    @Column(name = "UPDATE_DATE_TIME", nullable = false)
    private Timestamp updateDateTime;

    @OneToMany(mappedBy = "dataBundleUid", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ExtractLoadBundledAppTable> bundledAppTables = new ArrayList<>();

}