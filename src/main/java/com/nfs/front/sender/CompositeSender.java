package com.nfs.front.sender;

import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.SenderType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CompositeSender implements Sendable {
    private final HashMap<SenderType, Sendable> sendableMap;

    public CompositeSender(EmailNotificationSender emailNotificationSender,
                           SMSNotificationSender smsNotificationSender,
                           KakaoNotificationSender kakaoNotificationSender) {
        HashMap<SenderType, Sendable> sendableHashMap = new HashMap<>();
        sendableHashMap.put(SenderType.EMAIL, emailNotificationSender);
        sendableHashMap.put(SenderType.SMS, smsNotificationSender);
        sendableHashMap.put(SenderType.KAKAO_TALK, kakaoNotificationSender);
        this.sendableMap = sendableHashMap;
    }

    @Override
    public void send(Member member, Notification notification) {
        sendableMap.values().forEach(sendable -> sendable.send(member, notification));
    }

    public boolean retryBySenderType(Member member, Notification notification, SenderType senderType, Long notificationFailLogId) {
        Sendable sendable = sendableMap.get(senderType);
        if (sendable == null) {
            throw new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "sender type is wrong");
        }
        return sendable.retry(member, notification, notificationFailLogId);
    }

    @Override
    public boolean retry(Member member, Notification notification, Long notificationFailLogId) {
        return sendableMap.values().stream().allMatch(sendable -> sendable.retry(member, notification, notificationFailLogId));
    }
}
