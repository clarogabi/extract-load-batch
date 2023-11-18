package br.gov.sp.fatec.extractload.exception;


import org.springframework.http.HttpStatus;

public class UnprocessableEntityProblem extends ParentProblem {

    public UnprocessableEntityProblem(final String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public UnprocessableEntityProblem(final String message, final String info) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, info);
    }

}
