package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.SMS;
import com.nfs.front.service.NotificationLogService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SMSNotificationSender extends NotificationSender implements Sendable {


    public SMSNotificationSender(WebClient webClient, NotificationLogService notificationLogService) {
        super(webClient, notificationLogService);
    }

    @Override
    public void send(Member member, Notification notification) {
        SMS sms = new SMS(member.phoneNumber(), notification.title(), notification.content());
        sendMessageAndCreateLog(SenderType.SMS, sms, notification);
    }
}
