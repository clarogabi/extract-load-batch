package br.gov.sp.fatec.extractload.domain.dto;

import br.gov.sp.fatec.extractload.domain.enums.LoadModeEnum;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RowMappedDto {

    private Map<FieldMetadataDto, Object> row;
    private LoadModeEnum loadMode;

}
