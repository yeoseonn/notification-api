package com.nfs.front.error;

import com.nfs.front.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(ApiFailedException.class)
    public ResponseEntity<Object> handleApiFailedException(ApiFailedException apiFailedException) {
        ErrorCode errorCode = apiFailedException.getErrorCode();
        return new ResponseEntity<>(ApiResult.failureResult(errorCode, apiFailedException.getErrorMessage()), errorCode.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(ApiResult.failureResult(ErrorCode.SERVER_GENERAL_ERROR, e.getMessage()), ErrorCode.SERVER_GENERAL_ERROR.getHttpStatus());
    }
}
