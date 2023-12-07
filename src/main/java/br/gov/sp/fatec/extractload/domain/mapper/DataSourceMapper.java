package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.DataSourcePropertiesRequest;
import br.gov.sp.fatec.extractload.api.model.DataSourcePropertiesResponse;
import br.gov.sp.fatec.extractload.domain.dto.DataSourceDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDataSourceConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
    uses = { DatabaseDriverMapper.class },
    imports = { LocalDateTime.class })
public interface DataSourceMapper {

    @Mapping(target = "hostName", source = "databaseHost")
    @Mapping(target = "numberPort", source = "databaseNumberPort")
    @Mapping(target = "userName", source = "databaseUserName")
    @Mapping(target = "password", source = "databasePassword")
    @Mapping(target = "databaseDriver", source = "databaseProductName")
    DataSourceDto entityToDto(ExtractLoadDataSourceConfiguration entity);

    @Mapping(target = "databaseId", source = "uid")
    @Mapping(target = "schemaName", source = "databaseSchema")
    @Mapping(target = "productName", source = "databaseDriver")
    DataSourcePropertiesResponse dtoToResponse(DataSourceDto dto);

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "databaseSchema", source = "schemaName")
    @Mapping(target = "databaseDriver", source = "productName")
    DataSourceDto requestToDto(DataSourcePropertiesRequest request);

    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "databaseSchema", source = "request.schemaName")
    @Mapping(target = "databaseDriver", source = "request.productName")
    DataSourceDto requestToDto(Long uid, DataSourcePropertiesRequest request);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "databaseHost", source = "hostName")
    @Mapping(target = "databaseNumberPort", source = "numberPort")
    @Mapping(target = "databaseUserName", source = "userName")
    @Mapping(target = "databasePassword", source = "password")
    @Mapping(target = "databaseProductName", source = "databaseDriver")
    @Mapping(target = "createDateTime", expression = "java(LocalDateTime.now())")
    @Mapping(target = "updateDateTime", expression = "java(LocalDateTime.now())")
    ExtractLoadDataSourceConfiguration dtoToEntity(DataSourceDto dto);

    List<DataSourceDto> fromEntityList(List<ExtractLoadDataSourceConfiguration> datasourceList);

}
