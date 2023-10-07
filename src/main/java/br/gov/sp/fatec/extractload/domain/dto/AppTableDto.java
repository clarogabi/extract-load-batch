package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppTableDto {

    private Long uid;
    private String appTablePhysicalName;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

}
