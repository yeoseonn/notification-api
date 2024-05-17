package com.nfs.front.repository;

import com.nfs.front.entity.NotificationFailLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationFailLogRepository extends JpaRepository<NotificationFailLogEntity, Long> {

}
