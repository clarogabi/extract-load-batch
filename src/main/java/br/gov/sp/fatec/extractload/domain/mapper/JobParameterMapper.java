package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.JobParametersRequest;
import br.gov.sp.fatec.extractload.domain.dto.JobParametersDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobParameterMapper {

    @Mapping(target = "tables", ignore = true)
    @Mapping(target = "bundleName", ignore = true)
    JobParametersDto map(JobParametersRequest request);

}
