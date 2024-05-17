package com.nfs.front.sender;

import com.nfs.front.model.Member;
import com.nfs.front.model.Notification;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class CompositeSender implements Sendable {
    private final List<Sendable> sendableList;

    public CompositeSender(List<Sendable> sendableList) {
        if (CollectionUtils.isEmpty(sendableList)) {
            throw new IllegalArgumentException("cannot initialize CompositeAuthenticator");
        }
        this.sendableList = sendableList;
    }

    @Override
    public void send(Member member, Notification notification) {
        sendableList.forEach(sendable -> sendable.send(member, notification));
    }
}
