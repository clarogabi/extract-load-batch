package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class JobParametersDto {

    private Long dataBundleId;
    private String bundleName;
    private List<BundledAppTableDto> tables;

}
