package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.NotificationType;
import com.nfs.front.model.SenderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompositeSenderTest {
    EmailNotificationSender emailNotificationSender;
    SMSNotificationSender smsNotificationSender;
    KakaoNotificationSender kakaoNotificationSender;

    CompositeSender compositeSender;

    @BeforeEach
    void setUp() {
        emailNotificationSender = mock(EmailNotificationSender.class);
        smsNotificationSender = mock(SMSNotificationSender.class);
        kakaoNotificationSender = mock(KakaoNotificationSender.class);

        compositeSender = new CompositeSender(emailNotificationSender, smsNotificationSender, kakaoNotificationSender);
    }

    @Test
    void send() {
        //given
        Member member = new Member(1004L, "talkId", "yeoseon.yun@kakao.com", "010222222");
        Notification notification = new Notification(22L, member.memberId(), NotificationType.IMMEDIATE, null, "test", "test");

        //when
        compositeSender.send(member, notification);

        //then
        verify(emailNotificationSender).send(member, notification);
        verify(smsNotificationSender).send(member, notification);
        verify(kakaoNotificationSender).send(member, notification);
    }

    @Test
    void retryBySenderType() {
        //given
        Member member = new Member(1004L, "talkId", "yeoseon.yun@kakao.com", "010222222");
        Notification notification = new Notification(22L, member.memberId(), NotificationType.IMMEDIATE, null, "test", "test");
        Long notificationFailId = 2243L;

        //when
        compositeSender.retryBySenderType(member, notification, SenderType.KAKAO_TALK, notificationFailId);

        //then
        verify(kakaoNotificationSender).retry(member, notification, notificationFailId);
        verify(smsNotificationSender, never()).retry(member, notification, notificationFailId);
        verify(emailNotificationSender, never()).retry(member, notification, notificationFailId);
    }
}