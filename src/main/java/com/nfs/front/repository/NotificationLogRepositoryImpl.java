package com.nfs.front.repository;

import com.nfs.front.entity.NotificationLogEntity;
import com.nfs.front.entity.QNotificationEntity;
import com.nfs.front.entity.QNotificationLogEntity;
import com.nfs.front.model.NotificationLog;
import com.nfs.front.model.QNotificationLog;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationLogRepositoryImpl extends QuerydslRepositorySupport implements PageReturn<NotificationLog>, NotificationLogRepositoryCustom {
    public NotificationLogRepositoryImpl() {
        super(NotificationLogEntity.class);
    }

    @Override
    public Page<NotificationLog> getNotificationLogsByMemberId(Long memberId, Pageable pageable) {
        QNotificationEntity notificationEntity = QNotificationEntity.notificationEntity;
        QNotificationLogEntity notificationLogEntity = QNotificationLogEntity.notificationLogEntity;

        JPQLQuery<NotificationLog> query = from(notificationLogEntity).innerJoin(notificationEntity).on(notificationLogEntity.notificationId.eq(notificationEntity.notificationId))
                                                                      .where(notificationEntity.memberId.eq(memberId))
                                                                      .select(new QNotificationLog(notificationLogEntity.notificationLogId,
                                                                                                   notificationEntity.notificationId,
                                                                                                   notificationEntity.memberId,
                                                                                                   notificationLogEntity.senderType,
                                                                                                   notificationLogEntity.sentAt));

        return queryToPage(query, pageable, getQuerydsl());
    }
}
