package com.nfs.front.service;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationFailLogEntity;
import com.nfs.front.entity.NotificationLogEntity;
import com.nfs.front.model.Member;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.ResultCode;
import com.nfs.front.repository.NotificationFailLogRepository;
import com.nfs.front.repository.NotificationLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationLogServiceTest {
    NotificationLogRepository notificationLogRepository;
    NotificationFailLogRepository notificationFailLogRepository;
    MemberService memberService;

    NotificationLogService notificationLogService;

    Member member;

    @BeforeEach
    void setUp() {
        notificationLogRepository = mock(NotificationLogRepository.class);
        notificationFailLogRepository = mock(NotificationFailLogRepository.class);
        memberService = mock(MemberService.class);

        notificationLogService = new NotificationLogService(notificationLogRepository, notificationFailLogRepository, memberService);

        member = new Member(1004L, "talkId", "yeoseon.yun@kakao.com", "010222222");
    }

    @Test
    void getNotificationLogsByMemberId() {
        //given
        Long memberId = member.memberId();
        when(memberService.getMemberById(memberId)).thenReturn(member);
        Pageable pageable = PageRequest.of(0, 10);
        //when
        notificationLogService.getNotificationLogsByMemberId(memberId, pageable);
        //then
        verify(notificationLogRepository).getNotificationLogsByMemberId(memberId, pageable);
    }

    @Test
    void createNotificationSuccessLog() {
        //when
        notificationLogService.createNotificationLog(111L, LocalDateTime.now(), SenderType.SMS, ResultCode.SUCCESS);

        //then
        verify(notificationLogRepository).save(any(NotificationLogEntity.class));
    }

    @Test
    void createNotificationFailLog() {
        //when
        notificationLogService.createNotificationLog(111L, LocalDateTime.now(), SenderType.EMAIL, ResultCode.FAIL);

        //then
        verify(notificationFailLogRepository).save(any(NotificationFailLogEntity.class));

    }
}