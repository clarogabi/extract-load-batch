package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DataBundleDto {

    private Long uid;
    private String dataBundleName;
    private Set<BundledAppTableDto> bundledAppTables;

}
