package com.nfs.front.converter;

import com.nfs.front.model.SenderType;
import jakarta.persistence.AttributeConverter;

public class SenderTypeConverter implements AttributeConverter<SenderType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SenderType senderType) {
        return senderType.getValue();
    }

    @Override
    public SenderType convertToEntityAttribute(Integer dbData) {
        return SenderType.valueOf(dbData);
    }
}
