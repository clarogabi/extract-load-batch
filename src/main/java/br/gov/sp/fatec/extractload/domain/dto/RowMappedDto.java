package br.gov.sp.fatec.extractload.domain.dto;

import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RowMappedDto {

    private Map<FieldMetadataDto, Object> row;
    private LoadModeEnum loadMode;

}
