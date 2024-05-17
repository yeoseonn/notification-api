package com.nfs.front.entity;

import com.nfs.front.common.IdGenerator;
import com.nfs.front.converter.NotificationTypeConverter;
import com.nfs.front.model.NotificationRequest;
import com.nfs.front.model.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Notifications")
public class NotificationEntity {
    @Id
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "notification_type")
    @Convert(converter = NotificationTypeConverter.class)
    private NotificationType type;

    @Column(name = "notification_time")
    private LocalDateTime time;

    @Column(name = "content")
    private String content;

    @Column(name = "title")
    private String title;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static NotificationEntity newInstance(NotificationRequest notificationRequest) {
        return new NotificationEntity(IdGenerator.generate(), notificationRequest.memberId(), notificationRequest.type(), notificationRequest.time(), notificationRequest.title(),
                                      notificationRequest.content(),
                                      LocalDateTime.now());
    }
}
