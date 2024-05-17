CREATE TABLE IF NOT EXISTS Members (
    member_id BIGINT NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    talk_id VARCHAR(30) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    PRIMARY KEY (member_id)
);

CREATE TABLE IF NOT EXISTS Notifications (
    notification_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    notification_type TINYINT NOT NULL,
    notification_time DATETIME DEFAULT NULL,
    content TEXT DEFAULT NULL,
    title VARCHAR(300) DEFAULT NULL,
    created_at DATETIME NOT NULL,
    PRIMARY KEY (notification_id),
    CONSTRAINT   fk_Notifications_Members
    FOREIGN KEY (member_id)
    REFERENCES Members (member_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS NotificationItems(
    notification_item_id BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    notify_at DATETIME NOT NULL,
    PRIMARY KEY (notification_id),
    CONSTRAINT   fk_NotificationsItems_Notifications
    FOREIGN KEY (notification_id)
    REFERENCES Notifications (notification_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS NotificationLogs (
    notification_log_id BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    sent_at DATETIME NOT NULL,
    sender_type TINYINT NOT NULL,
    PRIMARY KEY (notification_id),
    CONSTRAINT   fk_NotificationLogs_Notifications
    FOREIGN KEY (notification_id)
    REFERENCES Notifications (notification_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS NotificationFailLogs (
    notification_fail_log_id BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    failed_at DATETIME NOT NULL,
    sender_type TINYINT NOT NULL,
    PRIMARY KEY (notification_id),
    CONSTRAINT   fk_NotificatioFailLogs_Notifications
    FOREIGN KEY (notification_id)
    REFERENCES Notifications (notification_id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);