package com.goose.pbtest.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    INTERNAL_ERROR(500),
    NOT_FOUND(404);

    private final int httpError;
}
