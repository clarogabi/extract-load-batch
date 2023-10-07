package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.BatchStatusEnum;
import br.gov.sp.fatec.extractload.api.model.ExtractionTypeEnum;
import br.gov.sp.fatec.extractload.domain.enums.DataExtractionTypeEnum;
import org.mapstruct.Mapper;
import org.springframework.batch.core.BatchStatus;

@Mapper(componentModel="spring")
public interface DataExtractionTypeMapper {

     DataExtractionTypeEnum map(ExtractionTypeEnum dataExtractionTypeEnum);
}
