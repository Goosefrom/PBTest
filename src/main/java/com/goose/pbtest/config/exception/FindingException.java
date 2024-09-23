package com.goose.pbtest.config.exception;

import lombok.Getter;

@Getter
public class FindingException  extends RuntimeException {

    private final ErrorType errorType;

    public FindingException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
}
