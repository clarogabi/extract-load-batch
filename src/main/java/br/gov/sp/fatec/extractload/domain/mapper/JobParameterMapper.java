package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.JobParametersRequest;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = DataExtractionTypeMapper.class)
public interface JobParameterMapper {

    JobParametersDto map(JobParametersRequest request);
}
