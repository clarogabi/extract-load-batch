package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.DatabaseProductEnum;
import br.gov.sp.fatec.extractload.domain.enums.DatabaseDriverEnum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DatabaseDriverMapper {

    DatabaseDriverEnum map(DatabaseProductEnum databaseProductEnum);

}
