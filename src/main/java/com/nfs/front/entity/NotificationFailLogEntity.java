package com.nfs.front.entity;

import com.nfs.front.common.IdGenerator;
import com.nfs.front.model.SenderType;
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
@Table(name = "NotificationFailLogs")
public class NotificationFailLogEntity {
    @Id
    @Column(name = "notification_fail_log_id")
    private Long notificationFailLogId;

    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @Column(name = "sender_type")
    private SenderType senderType;

    @ManyToOne
    @JoinColumn(name = "notification_id", insertable = false, updatable = false)
    private NotificationEntity notificationEntity;

    public NotificationFailLogEntity(Long notificationFailLogId, Long notificationId, LocalDateTime failedAt, SenderType senderType) {
        this.notificationFailLogId = notificationFailLogId;
        this.notificationId = notificationId;
        this.senderType = senderType;
        this.failedAt = failedAt;
    }


    public static NotificationFailLogEntity newInstance(Long notificationId, LocalDateTime failedAt, SenderType senderType) {
        return new NotificationFailLogEntity(IdGenerator.generate(), notificationId, failedAt, senderType);
    }
}
