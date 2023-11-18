package br.gov.sp.fatec.extractload.domain.dto;

public record FieldMetadataDto(int index,
                               String name,
                               int jdbcType,
                               boolean primaryKey) {

}
