package com.nfs.front.repository;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLogEntity, Long>, NotificationLogRepositoryCustom {
    Page<NotificationLogEntity> findBySentAtBefore(LocalDateTime dateTime, Pageable pageable);

}
