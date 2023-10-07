package br.gov.sp.fatec.extractload.domain.dto;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BundledAppTableDto {

    private Long uid;
    private String sourceAppTableName;
    private String targetAppTableName;
    private Long relationalOrderingNumber;
    private String extractCustomQuery;
    private String loadCustomInsertQuery;
    private String loadCustomUpdateQuery;
    public boolean hasReaderQuery() {
        return StringUtils.isNotEmpty(this.extractCustomQuery);
    }
}
