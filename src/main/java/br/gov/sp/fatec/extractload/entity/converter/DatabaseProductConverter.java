package br.gov.sp.fatec.extractload.entity.converter;

import br.gov.sp.fatec.extractload.domain.enums.DatabaseDriverEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static java.util.Objects.isNull;

@Converter(autoApply = true)
public class DatabaseProductConverter implements AttributeConverter<DatabaseDriverEnum, String> {

    @Override
    public String convertToDatabaseColumn(final DatabaseDriverEnum databaseDriverEnum) {
        return isNull(databaseDriverEnum) ? null : databaseDriverEnum.getProductName();
    }

    @Override
    public DatabaseDriverEnum convertToEntityAttribute(final String attribute) {
        return isNull(attribute) ? null : DatabaseDriverEnum.fromProductName(attribute);
    }

}
