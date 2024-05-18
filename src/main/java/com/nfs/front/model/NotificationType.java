package com.nfs.front.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;

import java.util.Arrays;

public enum NotificationType {
    IMMEDIATE(1),
    SCHEDULED(2);

    private Integer value;


    NotificationType(Integer value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }

    public static NotificationType valueOf(Integer value) {
        return Arrays.stream(NotificationType.values())
                     .filter(notificationType -> notificationType.getValue() == value)
                     .findFirst()
                     .orElseThrow(() -> new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "cannot match notification value"));
    }

    @JsonCreator
    public static NotificationType fromValue(String value) {
        return Arrays.stream(NotificationType.values())
                     .filter(notificationType -> notificationType.name().equalsIgnoreCase(value))
                     .findFirst()
                     .orElseThrow(() -> new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "cannot match notification type"));
    }

}
