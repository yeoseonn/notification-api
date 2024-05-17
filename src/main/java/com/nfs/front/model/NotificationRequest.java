package com.nfs.front.model;

import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;

import java.time.LocalDateTime;
import java.util.Objects;

public record NotificationRequest(Long memberId, NotificationType type, LocalDateTime time, String content, String title) {

    public NotificationRequest {
        if (type == NotificationType.SCHEDULED && Objects.isNull(time)) {
            throw new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "time cannot be null");
        }
        if (type == NotificationType.SCHEDULED && time.isBefore(LocalDateTime.now())) {
            throw new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "scheduled time is invalid");
        }
    }
}
