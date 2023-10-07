package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring", uses = { BundledAppTableMapper.class })
public interface DataBundleMapper {

    DataBundleDto mapToDto(ExtractLoadDataBundle entity);

}
