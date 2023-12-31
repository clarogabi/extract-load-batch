package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.DataTableRequest;
import br.gov.sp.fatec.extractload.api.model.DataTableResponse;
import br.gov.sp.fatec.extractload.domain.dto.AppTableDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadAppTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring",
    imports = { LocalDateTime.class })
public interface AppTableMapper {

    AppTableDto entityToDto(ExtractLoadAppTable entity);

    @Mapping(target = "tableId", source = "uid")
    @Mapping(target = "tablePhysicalName", source = "appTablePhysicalName")
    DataTableResponse dtoToResponse(AppTableDto dto);

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "appTablePhysicalName", source = "tablePhysicalName")
    AppTableDto requestToDto(DataTableRequest request);

    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "appTablePhysicalName", source = "request.tablePhysicalName")
    AppTableDto requestToDto(Long uid, DataTableRequest request);

    @Mapping(target = "createDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(LocalDateTime.now())")
    ExtractLoadAppTable dtoToEntity(AppTableDto dto);

}
