package br.gov.sp.fatec.extractload.exception;

import com.google.errorprone.annotations.Immutable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

@Immutable
public class NotFoundProblem extends AbstractThrowableProblem {

    private static final URI TYPE = URI.create("https://http.cat/404");

    public NotFoundProblem(String message) {
        super(TYPE, "Not found", Status.NOT_FOUND, message);
    }

}
