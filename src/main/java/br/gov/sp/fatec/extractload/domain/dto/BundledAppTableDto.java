package br.gov.sp.fatec.extractload.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class BundledAppTableDto {

    private Long uid;
    private Long sourceAppTableId;
    private String sourceAppTableName;
    private Long targetAppTableId;
    private String targetAppTableName;
    private Long relationalOrderingNumber;
    private String extractCustomQuery;
    private String loadCustomInsertQuery;
    private String loadCustomUpdateQuery;
    public boolean hasReaderQuery() {
        return StringUtils.isNotEmpty(this.extractCustomQuery);
    }
}
