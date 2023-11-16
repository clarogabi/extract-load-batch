package br.gov.sp.fatec.extractload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

import static java.util.Objects.nonNull;

public class ParentProblem extends ErrorResponseException {

    public ParentProblem(final HttpStatus status, final String message) {
        super(status, asProblemDetail(status, message, null), new Throwable(message));
    }

    public ParentProblem(final HttpStatus status, final String message, final String info) {
        super(status, asProblemDetail(status, message, info), new Throwable(message));
    }

    private static ProblemDetail asProblemDetail(final HttpStatus status, final String message, final String info) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setType(URI.create(String.format("https://http.cat/%s", status.value())));
        problemDetail.setProperty("timestamp", Instant.now());
        if (nonNull(info)) {
            problemDetail.setProperty("info", info);
        }
        return problemDetail;
    }

}
