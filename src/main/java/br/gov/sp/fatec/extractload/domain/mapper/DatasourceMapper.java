package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.DatasourcePropertiesRequest;
import br.gov.sp.fatec.extractload.api.model.DatasourcePropertiesResponse;
import br.gov.sp.fatec.extractload.domain.dto.DatasourceDto;
import br.gov.sp.fatec.extractload.entity.ExtractLoadDatasourceConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(componentModel="spring", imports = { LocalDateTime.class, Timestamp.class })
public interface DatasourceMapper {

    DatasourceDto entityToDto(ExtractLoadDatasourceConfiguration entity);

    @Mapping(target = "databaseId", source = "uid")
    @Mapping(target = "userName", source = "databaseUserName")
    @Mapping(target = "password", source = "databasePassword")
    @Mapping(target = "jdbcConnectionUrl", source = "databaseConnectionUrl")
    DatasourcePropertiesResponse dtoToResponse(DatasourceDto dto);

    @Mapping(target = "uid", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "databaseUserName", source = "userName")
    @Mapping(target = "databasePassword", source = "password")
    @Mapping(target = "databaseConnectionUrl", source = "jdbcConnectionUrl")
    DatasourceDto requestToDto(DatasourcePropertiesRequest request);

    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "databaseUserName", source = "request.userName")
    @Mapping(target = "databasePassword", source = "request.password")
    @Mapping(target = "databaseConnectionUrl", source = "request.jdbcConnectionUrl")
    DatasourceDto requestToDto(Long uid, DatasourcePropertiesRequest request);

    @Mapping(target = "createDateTime", expression = "java(Timestamp.valueOf(LocalDateTime.now()))")
    @Mapping(target = "updateDateTime", expression = "java(Timestamp.valueOf(LocalDateTime.now()))")
    ExtractLoadDatasourceConfiguration dtoToEntity(DatasourceDto dto);

}
