package br.gov.sp.fatec.extractload.exception;


import org.springframework.http.HttpStatus;

public class UnprocessableEntityProblem extends ParentProblem {

    public UnprocessableEntityProblem(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

}
