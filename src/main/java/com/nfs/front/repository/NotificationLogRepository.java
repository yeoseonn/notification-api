package com.nfs.front.repository;

import com.nfs.front.entity.NotificationEntity;
import com.nfs.front.entity.NotificationLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLogEntity, Long>, NotificationLogRepositoryCustom {

}
