package com.nfs.front.error;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    USER_INVALID_ERROR(-1002, HttpStatus.UNPROCESSABLE_ENTITY),
    VALIDATION_FAIL_ERROR(-1003, HttpStatus.UNPROCESSABLE_ENTITY),
    SERVER_GENERAL_ERROR(-1004, HttpStatus.INTERNAL_SERVER_ERROR),
    RESOURCE_NOT_FOUND(-1005, HttpStatus.NOT_FOUND),
    NOTIFICATION_FAIL_ERROR(-1006, HttpStatus.INTERNAL_SERVER_ERROR);
    private final int code;
    private final HttpStatus httpStatus;

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    ErrorCode(int code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
