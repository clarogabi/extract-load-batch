package br.gov.sp.fatec.extractload.domain.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ThrowableListJoiner {

    public String map(List<Throwable> failureExceptions) {
        if (failureExceptions != null && !failureExceptions.isEmpty()) {
            return failureExceptions.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining(" | "));
        } else {
            return null;
        }
    }
}
