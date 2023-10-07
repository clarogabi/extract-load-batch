package br.gov.sp.fatec.extractload.domain.dto;

import br.gov.sp.fatec.extractload.domain.enums.DataExtractionTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JobParametersDto {

    private Long dataBundleId;
    private DataExtractionTypeEnum extractionType;

}
