package br.gov.sp.fatec.extractload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;
import java.time.Instant;

public class ParentProblem extends ErrorResponseException {

    public ParentProblem(final HttpStatus status, final String message) {
        super(status, asProblemDetail(status, message), new Throwable(message));
    }

    private static ProblemDetail asProblemDetail(final HttpStatus status, final String message) {
        var problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setType(URI.create(String.format("https://http.cat/%s", status.value())));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

}
