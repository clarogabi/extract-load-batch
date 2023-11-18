package br.gov.sp.fatec.extractload.domain.dto;

public record BundledAppTableDto(Long uid,
                                 Long sourceAppTableId,
                                 String sourceAppTableName,
                                 Long targetAppTableId,
                                 String targetAppTableName,
                                 Long relationalOrderingNumber,
                                 String extractCustomQuery,
                                 String loadCustomInsertQuery,
                                 String loadCustomUpdateQuery) {

}
