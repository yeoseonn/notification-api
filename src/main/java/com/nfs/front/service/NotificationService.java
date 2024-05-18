package com.nfs.front.service;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationItemEntity;
import com.nfs.front.event.NotificationSendEvent;
import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.NotificationRequest;
import com.nfs.front.model.NotificationType;
import com.nfs.front.repository.NotificationItemRepository;
import com.nfs.front.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationItemRepository notificationItemRepository;
    private final MemberService memberService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Notification registerNotification(NotificationRequest notificationRequest) {
        Member member = memberService.getMemberById(notificationRequest.memberId());
        NotificationEntity notificationEntity = notificationRepository.save(NotificationEntity.newInstance(notificationRequest));

        if (notificationEntity.getType() == NotificationType.IMMEDIATE) {
            applicationEventPublisher.publishEvent(new NotificationSendEvent(member, Notification.of(notificationEntity)));
        } else {
            notificationItemRepository.save(NotificationItemEntity.newInstance(notificationEntity));
        }
        return Notification.of(notificationEntity);
    }
}
