package br.gov.sp.fatec.extractload.domain.mapper;

import br.gov.sp.fatec.extractload.api.model.ExitStatusEnum;
import org.springframework.batch.core.ExitStatus;
import org.springframework.stereotype.Component;

@Component
public class ExitStatusMapper {

    public ExitStatusEnum map(ExitStatus exitStatus) {
        if (exitStatus != null) {
            return ExitStatusEnum.fromValue(exitStatus.getExitCode());
        } else {
            return null;
        }
    }
}
