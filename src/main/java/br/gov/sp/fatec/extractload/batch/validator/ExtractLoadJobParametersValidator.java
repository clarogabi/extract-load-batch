package br.gov.sp.fatec.extractload.batch.validator;

import br.gov.sp.fatec.extractload.domain.dto.BundledAppTableDto;
import br.gov.sp.fatec.extractload.domain.enums.DataExtractionTypeEnum;
import br.gov.sp.fatec.extractload.service.BundledAppTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class ExtractLoadJobParametersValidator implements JobParametersValidator {

    @Autowired
    private BundledAppTableService bundledAppTableService;

    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        log.info("Job parameters validation: [{}]", jobParameters);

        List<String> errorTables = new ArrayList<>();
        Long dataBundleId = jobParameters.getLong("dataBundleId");
        DataExtractionTypeEnum extractionType = DataExtractionTypeEnum
                .fromValue(jobParameters.getString("extractionType"));

        List<BundledAppTableDto> bundledAppTables = bundledAppTableService.findBundledAppTablesByDataBundleId(dataBundleId);

        if (DataExtractionTypeEnum.CUSTOM.equals(extractionType)) {
            bundledAppTables.forEach(table -> {
                String extractCustomQuery = table.getExtractCustomQuery();
                if (isNull(extractCustomQuery) || extractCustomQuery.isBlank()) {
                    errorTables.add(table.getSourceAppTableName());
                } else if (!extractCustomQuery.toUpperCase().contains("SELECT")
                        && !extractCustomQuery.toUpperCase().contains("FROM")) {
                    errorTables.add(table.getSourceAppTableName());
                }
            });

            if (!errorTables.isEmpty()) {
                log.error("Failed validating the parameters ");
                throw new JobParametersInvalidException("");
            }
        }
    }
}
