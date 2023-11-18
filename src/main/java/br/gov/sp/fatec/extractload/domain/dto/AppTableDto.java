package br.gov.sp.fatec.extractload.domain.dto;

import java.time.LocalDateTime;

public record AppTableDto(Long uid,
                          String appTablePhysicalName,
                          LocalDateTime createDateTime,
                          LocalDateTime updateDateTime) {

}
