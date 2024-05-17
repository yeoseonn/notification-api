package com.nfs.front.model;

import com.nfs.front.entity.NotificationEntity;

import java.time.LocalDateTime;

public record Notification(Long notificationId, Long memberId, NotificationType type, LocalDateTime time, String title, String content) {
    public static Notification of(NotificationEntity notificationEntity) {
        return new Notification(notificationEntity.getNotificationId(), notificationEntity.getMemberId(), notificationEntity.getType(), notificationEntity.getTime(), notificationEntity.getContent(), notificationEntity.getTitle());
    }
}
