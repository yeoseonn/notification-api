package com.nfs.front.common;

import com.nfs.front.error.ErrorCode;

import java.util.Optional;

public record ApiHeader(boolean isSuccessful, int resultCode, String resultMessage) {
    private static final int SUCCESS_RESULT_CODE = 0;

    public static ApiHeader successHeader() {
        return new ApiHeader(true, SUCCESS_RESULT_CODE, "");
    }

    public static ApiHeader failureHeader(ErrorCode errorCode, String message) {
        return new ApiHeader(false, errorCode.getCode(), Optional.of(message).orElse(errorCode.name()));
    }
}
