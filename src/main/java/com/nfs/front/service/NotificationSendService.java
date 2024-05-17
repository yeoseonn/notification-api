package com.nfs.front.service;

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

    public void sendNotification(Member member, Notification notification) {
        compositeSender.send(member, notification);
    }

    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendNotificationImmediate(NotificationSendEvent notificationSendEvent) {
        compositeSender.send(notificationSendEvent.member(), notificationSendEvent.notification());
    }
}
