package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataBundleDto {

    private Long uid;
    private String dataBundleName;
    private Long sourceDatasourceId;
    private Long targetDatasourceId;
    private List<BundledAppTableDto> bundledAppTables;

}
