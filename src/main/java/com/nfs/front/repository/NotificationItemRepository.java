package com.nfs.front.repository;

import com.nfs.front.entity.NotificationItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationItemRepository extends JpaRepository<NotificationItemEntity, Long> {

    Page<NotificationItemEntity> findByNotifyAtBetween(LocalDateTime startAt, LocalDateTime endAt, Pageable pageable);
}
