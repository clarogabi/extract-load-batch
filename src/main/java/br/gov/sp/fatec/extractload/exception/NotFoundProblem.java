package br.gov.sp.fatec.extractload.exception;

import com.google.errorprone.annotations.Immutable;
import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

import static org.zalando.problem.Status.NOT_FOUND;

@Immutable
public class NotFoundProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create("https://http.cat/404");

    public NotFoundProblem(String message) {
        super(TYPE, NOT_FOUND.getReasonPhrase(), NOT_FOUND, message);
    }

}
