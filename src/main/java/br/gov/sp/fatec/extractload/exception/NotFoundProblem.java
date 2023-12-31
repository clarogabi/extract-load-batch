package br.gov.sp.fatec.extractload.exception;


import org.springframework.http.HttpStatus;

public class NotFoundProblem extends ParentProblem {

    public NotFoundProblem(final String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
