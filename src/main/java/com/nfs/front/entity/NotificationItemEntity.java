package com.nfs.front.entity;

import com.nfs.front.common.IdGenerator;
import com.nfs.front.model.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "NotificationItems")
public class NotificationItemEntity {
    @Id
    @Column(name = "notification_item_id")
    private Long notificationItemId;

    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "notify_at")
    private LocalDateTime notifyAt;

    @ManyToOne
    @JoinColumn(name = "notification_id", insertable = false, updatable = false)
    private NotificationEntity notificationEntity;

    public NotificationItemEntity(Long notificationItemId, Long notificationId, LocalDateTime notifyAt) {
        this.notificationItemId = notificationItemId;
        this.notificationId = notificationId;
        this.notifyAt = notifyAt;
    }

    public static NotificationItemEntity newInstance(NotificationEntity notificationEntity) {
        LocalDateTime notifyAt = notificationEntity.getType() == NotificationType.IMMEDIATE ? LocalDateTime.now() : notificationEntity.getTime();
        return new NotificationItemEntity(IdGenerator.generate(), notificationEntity.getNotificationId(), notifyAt);
    }
}
