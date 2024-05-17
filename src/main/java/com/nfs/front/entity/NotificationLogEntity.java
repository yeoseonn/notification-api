package com.nfs.front.entity;

import com.nfs.front.common.IdGenerator;
import com.nfs.front.converter.SenderTypeConverter;
import com.nfs.front.model.SenderType;
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
@Table(name = "NotificationLogs")
public class NotificationLogEntity {
    @Id
    @Column(name = "notification_log_id")
    private Long notificationLogId;

    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "sender_type")
    @Convert(converter = SenderTypeConverter.class)
    private SenderType senderType;

    public static NotificationLogEntity newInstance(Long notificationId, LocalDateTime sentAt, SenderType senderType) {
        return new NotificationLogEntity(IdGenerator.generate(), notificationId, sentAt, senderType);
    }
}
