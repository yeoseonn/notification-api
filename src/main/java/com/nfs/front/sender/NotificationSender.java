package com.nfs.front.sender;

import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import com.nfs.front.model.Notification;
import com.nfs.front.model.SenderType;
import com.nfs.front.model.notification.Message;
import com.nfs.front.model.notification.Response;
import com.nfs.front.model.notification.ResultCode;
import com.nfs.front.service.NotificationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public abstract class NotificationSender {
    private final WebClient webClient;
    @Value("${notification.sender.server.host}")
    private String host;
    @Value("${notification.sender.server.port}")
    private int port;
    private static final ParameterizedTypeReference<Response> RESPONSE_PARAMETERIZED_TYPE_REFERENCE;
    private final NotificationLogService notificationLogService;


    static {
        RESPONSE_PARAMETERIZED_TYPE_REFERENCE = new ParameterizedTypeReference<>() {
        };
    }

    public void sendMessageAndCreateLog(SenderType senderType, Message message, Notification notification) {
        URI uri = UriComponentsBuilder.fromPath(senderType.getPath())
                                      .scheme("http").host(host).port(port)
                                      .build().toUri();
        Mono<ResultCode> mono = webClient.post()
                                         .uri(uri)
                                         .bodyValue(message)
                                         .retrieve()
                                         .onStatus(HttpStatusCode::isError, response -> Mono.error(new ApiFailedException(ErrorCode.NOTIFICATION_FAIL_ERROR)))
                                         .bodyToMono(RESPONSE_PARAMETERIZED_TYPE_REFERENCE)
                                         .map(Response::resultCode)
                                         .onErrorReturn(ResultCode.FAIL);


        mono.subscribe(resultCode -> {
            notificationLogService.createNotificationLog(notification.notificationId(), LocalDateTime.now(), senderType, resultCode);
        });
    }


}
