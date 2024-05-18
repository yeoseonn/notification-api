package com.nfs.front.service;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationFailLogEntity;
import com.nfs.front.entity.NotificationItemEntity;
import com.nfs.front.event.NotificationSendEvent;
import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.sender.CompositeSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class NotificationSendService {
    private final CompositeSender compositeSender;
    private final MemberService memberService;

    public void sendNotification(NotificationItemEntity notificationItemEntity) {
        NotificationEntity notificationEntity = notificationItemEntity.getNotificationEntity();
        Member member = memberService.getMemberById(notificationEntity.getMemberId());
        compositeSender.send(member, Notification.of(notificationEntity));
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotificationImmediate(NotificationSendEvent notificationSendEvent) {
        compositeSender.send(notificationSendEvent.member(), notificationSendEvent.notification());
    }

    public boolean retrySendNotification(NotificationFailLogEntity notificationFailLogEntity) {
        NotificationEntity notificationEntity = notificationFailLogEntity.getNotificationEntity();
        Member member = memberService.getMemberById(notificationEntity.getMemberId());
        return compositeSender.retryBySenderType(member, Notification.of(notificationEntity), notificationFailLogEntity.getSenderType(), notificationFailLogEntity.getNotificationFailLogId());
    }
}
