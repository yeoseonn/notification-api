package com.nfs.front.error;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApiFailedException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public ApiFailedException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ApiFailedException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.name();
    }
}
