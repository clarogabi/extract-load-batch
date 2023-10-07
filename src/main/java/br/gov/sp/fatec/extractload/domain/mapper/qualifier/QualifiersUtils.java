package br.gov.sp.fatec.extractload.domain.mapper.qualifier;

import br.gov.sp.fatec.extractload.api.model.ExitStatusEnum;
import org.springframework.batch.core.ExitStatus;

import java.util.List;
import java.util.stream.Collectors;

public class QualifiersUtils {

    @ThrowableListJoiner
    public String throwableListJoiner(List<Throwable> failureExceptions) {
        if (failureExceptions != null && !failureExceptions.isEmpty()) {
            return failureExceptions.stream()
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining(" | "));
        } else {
            return null;
        }
    }

    @ExitStatusMapper
    public ExitStatusEnum exitStatusMapper(ExitStatus exitStatus) {
        if (exitStatus != null) {
            return ExitStatusEnum.fromValue(exitStatus.getExitCode());
        } else {
            return null;
        }
    }
}
