package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.Email;
import com.nfs.front.service.NotificationLogService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EmailNotificationSender extends NotificationSender implements Sendable {

    public EmailNotificationSender(WebClient webClient, NotificationLogService notificationLogService) {
        super(webClient, notificationLogService);
    }

    @Override
    @Transactional
    public void send(Member member, Notification notification) {
        Email email = new Email(member.emailAddress(), notification.title(), notification.content());
        sendMessageAndCreateLog(SenderType.EMAIL, email, notification);
    }

    @Override
    public boolean retry(Member member, Notification notification, Long notificationFailLogId) {
        Email email = new Email(member.emailAddress(), notification.title(), notification.content());
        return retrySendMessage(SenderType.EMAIL, email, notification, notificationFailLogId);
    }
}
