package br.gov.sp.fatec.extractload.domain.dto;

import java.util.List;

public record DataBundleDto(Long uid,
                            String dataBundleName,
                            Long sourceDataSourceId,
                            Long targetDataSourceId,
                            List<BundledAppTableDto> bundledAppTables) {

}
