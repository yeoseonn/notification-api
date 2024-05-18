package com.nfs.front.service;

import com.nfs.front.entity.NotificationFailLogEntity;
import com.nfs.front.entity.NotificationLogEntity;
import com.nfs.front.model.Member;
import com.nfs.front.model.NotificationLog;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.ResultCode;
import com.nfs.front.repository.NotificationFailLogRepository;
import com.nfs.front.repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationLogService {
    private final NotificationLogRepository notificationLogRepository;
    private final NotificationFailLogRepository notificationFailLogRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public Page<NotificationLog> getNotificationLogsByMemberId(Long memberId, Pageable pageable) {
        Member member = memberService.getMemberById(memberId);
        return notificationLogRepository.getNotificationLogsByMemberId(memberId, pageable);
    }

    @Transactional
    public void createNotificationLog(Long notificationId, LocalDateTime sentAt, SenderType senderType, ResultCode resultCode) {
        if (resultCode == ResultCode.SUCCESS) {
            createNotificationSuccessLog(notificationId, sentAt, senderType);
        } else {
            createNotificationFailLog(notificationId, sentAt, senderType);
        }
    }


    @Transactional
    public void createRetrySuccessLog(Long notificationId, LocalDateTime sentAt, SenderType senderType, Long notificationFailLogId) {
        createNotificationSuccessLog(notificationId, sentAt, senderType);
    }

    private void createNotificationSuccessLog(Long notificationId, LocalDateTime sentAt, SenderType senderType) {
        NotificationLogEntity notificationLogEntity = NotificationLogEntity.newInstance(notificationId, sentAt, senderType);
        notificationLogRepository.save(notificationLogEntity);
    }

    private void createNotificationFailLog(Long notificationId, LocalDateTime sentAt, SenderType senderType) {
        NotificationFailLogEntity notificationFailLogEntity = NotificationFailLogEntity.newInstance(notificationId, sentAt, senderType);
        notificationFailLogRepository.save(notificationFailLogEntity);
    }
}
