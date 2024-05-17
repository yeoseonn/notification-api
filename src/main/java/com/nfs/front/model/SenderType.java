package com.nfs.front.model;

import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import lombok.Getter;

import java.util.Arrays;

public enum SenderType {
    KAKAO_TALK(1, "/send/kakaotalk"),
    SMS(2, "/send/sms"),
    EMAIL(3, "/send/email");

    @Getter
    private int value;
    @Getter
    private String path;


    SenderType(int value, String path) {
        this.value = value;
        this.path = path;
    }

    public static SenderType valueOf(Integer value) {
        return Arrays.stream(SenderType.values())
                     .filter(senderType -> senderType.getValue() == value)
                     .findFirst()
                     .orElseThrow(() -> new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "cannot match sender type value"));
    }
}
