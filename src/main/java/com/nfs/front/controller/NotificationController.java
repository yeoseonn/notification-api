package com.nfs.front.controller;

import com.nfs.front.common.ApiResult;
import com.nfs.front.model.NotificationRequest;
import com.nfs.front.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/notifications")
    public ApiResult registerNotification(@RequestBody NotificationRequest notificationRequest){
        notificationService.registerNotification(notificationRequest);
        return ApiResult.success();
    }
}
