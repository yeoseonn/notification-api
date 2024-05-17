package com.nfs.front.model;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record NotificationLog(Long notificationLogId, Long notificationId, Long memberId, SenderType senderType, LocalDateTime sentAt) {
    @QueryProjection
    public NotificationLog {
    }
}
