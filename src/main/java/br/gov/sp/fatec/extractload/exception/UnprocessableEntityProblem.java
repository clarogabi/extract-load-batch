package br.gov.sp.fatec.extractload.exception;

import com.google.errorprone.annotations.Immutable;
import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

import static org.zalando.problem.Status.UNPROCESSABLE_ENTITY;

@Immutable
public class UnprocessableEntityProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create("https://http.cat/422");

    public UnprocessableEntityProblem(String message) {
        super(TYPE, UNPROCESSABLE_ENTITY.getReasonPhrase(), UNPROCESSABLE_ENTITY, message);
    }

}
