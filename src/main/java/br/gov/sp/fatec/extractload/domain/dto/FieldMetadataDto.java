package br.gov.sp.fatec.extractload.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMetadataDto {

    private int index;
    private String name;
    private int jdbcType;
    private boolean primaryKey = false;

}
