package com.nfs.front.model.notification;

import lombok.Value;

@Value
public class KakaoTalk implements Message {
    String talkId;
    String title;
    String contents;
}
