package br.gov.sp.fatec.extractload.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DataExtractionTypeEnum {

    DEFAULT("DEFAULT"),
    CUSTOM("CUSTOM");
    private String value;

    DataExtractionTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static DataExtractionTypeEnum fromValue(String value) {
        for (DataExtractionTypeEnum item : DataExtractionTypeEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
