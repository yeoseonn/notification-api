package com.nfs.front.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "Members")
public class MemberEntity {
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "talk_id")
    private String talkId;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
