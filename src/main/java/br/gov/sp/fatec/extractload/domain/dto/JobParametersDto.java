package br.gov.sp.fatec.extractload.domain.dto;

import java.util.List;

public record JobParametersDto(Long dataBundleId,
                               String bundleName,
                               List<BundledAppTableDto> tables) {

}
