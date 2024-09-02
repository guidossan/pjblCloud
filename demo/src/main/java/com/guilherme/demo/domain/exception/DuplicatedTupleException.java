package com.guilherme.demo.domain.exception;

public class DuplicatedTupleException extends RuntimeException{
    public DuplicatedTupleException(String msg){
        super(msg);
    }
}
