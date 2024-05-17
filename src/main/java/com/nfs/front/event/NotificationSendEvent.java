package com.nfs.front.event;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;

public record NotificationSendEvent(Member member, Notification notification) {

}
