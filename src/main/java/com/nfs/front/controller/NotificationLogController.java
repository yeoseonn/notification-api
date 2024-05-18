package com.nfs.front.controller;

import com.nfs.front.common.ApiResults;
import com.nfs.front.model.NotificationLog;
import com.nfs.front.service.NotificationLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationLogController {
    private final NotificationLogService notificationLogService;

    @GetMapping(value = "/notifications/logs", params = "memberId")
    public ApiResults<NotificationLog> registerNotification(@RequestParam Long memberId, @PageableDefault(size = 20) Pageable pageable) {
        return ApiResults.success(notificationLogService.getNotificationLogsByMemberId(memberId, pageable));
    }
}
