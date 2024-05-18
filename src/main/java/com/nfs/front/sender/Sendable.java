package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;

public interface Sendable {

    void send(Member member, Notification notification);

    boolean retry(Member member, Notification notification, Long notificationFailLogId);
}
