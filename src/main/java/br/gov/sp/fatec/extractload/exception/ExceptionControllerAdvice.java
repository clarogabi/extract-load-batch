package br.gov.sp.fatec.extractload.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@ControllerAdvice
public class ExceptionControllerAdvice implements ProblemHandling {

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

}
