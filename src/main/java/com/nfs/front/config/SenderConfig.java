package com.nfs.front.config;

import com.nfs.front.sender.CompositeSender;
import com.nfs.front.sender.EmailNotificationSender;
import com.nfs.front.sender.KakaoNotificationSender;
import com.nfs.front.sender.SMSNotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class SenderConfig {
    @Bean
    @Primary
    public CompositeSender compositeSender(EmailNotificationSender emailNotificationSender,
                                           SMSNotificationSender smsNotificationSender,
                                           KakaoNotificationSender kakaoNotificationSender) {
        return new CompositeSender(List.of(emailNotificationSender, smsNotificationSender, kakaoNotificationSender));
    }
}
