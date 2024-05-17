package com.nfs.front.model.notification;

import lombok.Value;

@Value
public class Email implements Message {
    String emailAddress;
    String title;
    String contents;
}
