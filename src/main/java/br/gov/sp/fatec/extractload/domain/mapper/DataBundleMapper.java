package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.BundleRequest;
import br.gov.sp.fatec.extractload.api.model.DataBundleRequest;
import br.gov.sp.fatec.extractload.api.model.DataBundleResponse;
import br.gov.sp.fatec.extractload.domain.dto.DataBundleDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataBundle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = { BundledAppTableMapper.class }, imports = { LocalDateTime.class })
public interface DataBundleMapper {

    @Mapping(target = "sourceDataSourceId", source = "sourceDataSourceConfig.uid")
    @Mapping(target = "targetDataSourceId", source = "targetDataSourceConfig.uid")
    DataBundleDto mapToDto(ExtractLoadDataBundle entity);

    @Mapping(target = "bundleId", source = "uid")
    @Mapping(target = "bundleName", source = "dataBundleName")
    @Mapping(target = "bundledTables", source = "bundledAppTables")
    DataBundleResponse mapToResponse(DataBundleDto dataBundleDto);

    @Mapping(target = "createDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "sourceDataSourceConfig.uid", source = "sourceDataSourceId")
    @Mapping(target = "targetDataSourceConfig.uid", source = "targetDataSourceId")
    ExtractLoadDataBundle dtoToEntity(DataBundleDto dataBundleDto);

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "dataBundleName", source = "bundleName")
    @Mapping(target = "bundledAppTables", source = "bundledTables")
    DataBundleDto requestToDto(DataBundleRequest dataBundleRequest);

    @Mapping(target = "bundledAppTables", ignore = true)
    @Mapping(target = "dataBundleName", source = "bundleRequest.bundleName")
    DataBundleDto requestToDto(Long uid, BundleRequest bundleRequest);

}
