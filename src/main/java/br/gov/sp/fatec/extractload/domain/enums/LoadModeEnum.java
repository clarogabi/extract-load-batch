package br.gov.sp.fatec.extractload.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoadModeEnum {

    INSERT("INSERT"),
    UPDATE("UPDATE");

    private final String value;

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
