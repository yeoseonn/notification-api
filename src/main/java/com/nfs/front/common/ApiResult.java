package com.nfs.front.common;


import com.nfs.front.error.ErrorCode;

public record ApiResult<T>(ApiHeader header, T result) {
    public static <T> ApiResult<T> success(T result) {
        return new ApiResult<>(ApiHeader.successHeader(), result);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>(ApiHeader.successHeader(), null);
    }

    public static <T> ApiResult<T> failureResult(ErrorCode errorCode, String errorMessage) {
        return new ApiResult<>(ApiHeader.failureHeader(errorCode, errorMessage), null);
    }
}