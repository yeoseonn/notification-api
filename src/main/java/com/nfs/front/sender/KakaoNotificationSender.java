package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.KakaoTalk;
import com.nfs.front.service.NotificationLogService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoNotificationSender extends NotificationSender implements Sendable {
    public KakaoNotificationSender(WebClient webClient, NotificationLogService notificationLogService) {
        super(webClient, notificationLogService);
    }

    @Override
    public void send(Member member, Notification notification) {
        KakaoTalk kakaoTalk = new KakaoTalk(member.talkId(), notification.title(), notification.content());
        sendMessageAndCreateLog(SenderType.KAKAO_TALK, kakaoTalk, notification);
    }
}
