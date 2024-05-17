package com.nfs.front.model.notification;

import lombok.Value;

@Value
public class SMS implements Message {
    String phoneNumber;
    String title;
    String contents;
}
