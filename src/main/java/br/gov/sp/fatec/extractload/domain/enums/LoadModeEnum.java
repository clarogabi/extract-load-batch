package br.gov.sp.fatec.extractload.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoadModeEnum {

    INSERT("INSERT"),
    UPDATE("UPDATE");
    private String value;

    LoadModeEnum(String value) {
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
    public static LoadModeEnum fromValue(String value) {
        for (LoadModeEnum item : LoadModeEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
