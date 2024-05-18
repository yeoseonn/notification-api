package com.nfs.front.service;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationItemEntity;
import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import com.nfs.front.event.NotificationSendEvent;
import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.NotificationRequest;
import com.nfs.front.model.NotificationType;
import com.nfs.front.repository.NotificationItemRepository;
import com.nfs.front.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {
    NotificationRepository notificationRepository;
    NotificationItemRepository notificationItemRepository;
    MemberService memberService;
    ApplicationEventPublisher applicationEventPublisher;

    NotificationService notificationService;

    Member member;

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        notificationItemRepository = mock(NotificationItemRepository.class);
        memberService = mock(MemberService.class);
        applicationEventPublisher = mock(ApplicationEventPublisher.class);

        notificationService = new NotificationService(notificationRepository, notificationItemRepository, memberService, applicationEventPublisher);

        member = new Member(1004L, "talkId", "yeoseon.yun@kakao.com", "010222222");
    }


    @Test
    public void registerImmediateNotification() {
        //given
        NotificationRequest notificationRequest = new NotificationRequest(member.memberId(), NotificationType.IMMEDIATE, null, "test", "title");
        when(memberService.getMemberById(notificationRequest.memberId())).thenReturn(member);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(NotificationEntity.newInstance(notificationRequest));
        //when
        Notification notification = notificationService.registerNotification(notificationRequest);

        //then
        verify(notificationRepository).save(any(NotificationEntity.class));
        verify(applicationEventPublisher).publishEvent(any(NotificationSendEvent.class));
        assertEquals(notificationRequest.memberId(), notification.memberId());
        assertEquals(notificationRequest.type(), notification.type());
    }

    @Test
    public void registerScheduledNotification() {
        //given
        NotificationRequest notificationRequest = new NotificationRequest(member.memberId(), NotificationType.SCHEDULED, LocalDateTime.now().plusHours(1), "test", "title");
        when(memberService.getMemberById(notificationRequest.memberId())).thenReturn(member);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(NotificationEntity.newInstance(notificationRequest));
        //when
        notificationService.registerNotification(notificationRequest);

        //then
        verify(notificationRepository).save(any(NotificationEntity.class));
        verify(notificationItemRepository).save(any(NotificationItemEntity.class));

    }


    @Test
    public void registerWrongScheduledNotification() {
        //when
        //scheulded type에 time이 null일 수 없음.
        ApiFailedException exception = assertThrows(ApiFailedException.class, () -> new NotificationRequest(member.memberId(), NotificationType.SCHEDULED, null, "test", "title"));
        //then
        assertEquals(ErrorCode.USER_INVALID_ERROR, exception.getErrorCode());
    }

}