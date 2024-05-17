package com.nfs.front.repository;

import com.nfs.front.model.NotificationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface NotificationLogRepositoryCustom {
    Page<NotificationLog> getNotificationLogsByMemberId(Long memberId, Pageable pageable);
}
