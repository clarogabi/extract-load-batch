package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.BundledTableRequest;
import br.gov.sp.fatec.extractload.api.model.BundledTableResponse;
import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadBundledAppTable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
    uses = { AppTableMapper.class },
    imports = { LocalDateTime.class })
public interface BundledAppTableMapper {

    @Mapping(target = "sourceAppTableId", source = "sourceAppTable.uid")
    @Mapping(target = "sourceAppTableName", source = "sourceAppTable.appTablePhysicalName")
    @Mapping(target = "targetAppTableId", source = "targetAppTable.uid")
    @Mapping(target = "targetAppTableName", source = "targetAppTable.appTablePhysicalName")
    BundledAppTableDto mapToDto(ExtractLoadBundledAppTable entity);

    List<BundledAppTableDto> mapToDtoList(List<ExtractLoadBundledAppTable> entityList);

    @Mapping(target = "bundledTableId", source = "uid")
    @Mapping(target = "sourceTableId", source = "sourceAppTableId")
    @Mapping(target = "targetTableId", source = "targetAppTableId")
    @Mapping(target = "tablePhysicalName", source = "sourceAppTableName")
    @Mapping(target = "relationalOrdering", source = "relationalOrderingNumber")
    @Mapping(target = "querySelect", source = "extractCustomQuery")
    @Mapping(target = "queryInsert", source = "loadCustomInsertQuery")
    @Mapping(target = "queryUpdate", source = "loadCustomUpdateQuery")
    BundledTableResponse mapToResponse(BundledAppTableDto bundledAppTableDto);

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "sourceAppTableName", ignore = true)
    @Mapping(target = "targetAppTableName", ignore = true)
    @Mapping(target = "sourceAppTableId", source = "sourceTableId")
    @Mapping(target = "targetAppTableId", source = "targetTableId")
    @Mapping(target = "relationalOrderingNumber", source = "relationalOrdering")
    @Mapping(target = "extractCustomQuery", source = "querySelect")
    @Mapping(target = "loadCustomInsertQuery", source = "queryInsert")
    @Mapping(target = "loadCustomUpdateQuery", source = "queryUpdate")
    BundledAppTableDto requestToDto(BundledTableRequest bundledTableRequest);

    List<BundledAppTableDto> requestToDtoList(List<BundledTableRequest> bundledTableRequestList);

    @Mapping(target = "extractLoadDataBundle", ignore = true)
    @Mapping(target = "sourceAppTable.uid", source = "sourceAppTableId")
    @Mapping(target = "targetAppTable.uid", source = "targetAppTableId")
    @Mapping(target = "createDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(LocalDateTime.now())")
    ExtractLoadBundledAppTable mapToEntity(BundledAppTableDto bundledAppTableDto);

    @Mapping(target = "extractLoadDataBundle.uid", source = "dataBundleUid")
    @Mapping(target = "sourceAppTable.uid", source = "dto.sourceAppTableId")
    @Mapping(target = "targetAppTable.uid", source = "dto.targetAppTableId")
    @Mapping(target = "createDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(LocalDateTime.now())")
    ExtractLoadBundledAppTable mapToEntityForAddition(BundledAppTableDto dto, Long dataBundleUid);

    List<ExtractLoadBundledAppTable> mapToEntityList(List<BundledAppTableDto> bundledAppTableDtoList);

}
