package com.nfs.front.converter;

import com.nfs.front.model.NotificationType;
import com.nfs.front.model.SenderType;
import jakarta.persistence.AttributeConverter;

public class NotificationTypeConverter implements AttributeConverter<NotificationType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationType notificationType) {
        return notificationType.getValue();
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer dbData) {
        return NotificationType.valueOf(dbData);
    }
}
