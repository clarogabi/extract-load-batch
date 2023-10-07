package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel="spring")
public interface BundledAppTableMapper {

    @Mapping(target = "sourceAppTableName", source = "sourceAppTable.appTablePhysicalName")
    @Mapping(target = "targetAppTableName", source = "targetAppTable.appTablePhysicalName")
    BundledAppTableDto mapToDto(ExtractLoadBundledAppTable entity);

    List<BundledAppTableDto> mapList(List<ExtractLoadBundledAppTable> entityList);

}
