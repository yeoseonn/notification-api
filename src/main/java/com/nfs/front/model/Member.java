package com.nfs.front.model;

import com.nfs.front.entity.MemberEntity;

public record Member(Long memberId, String talkId, String emailAddress, String phoneNumber) {
    public static Member of(MemberEntity memberEntity) {
        return new Member(memberEntity.getMemberId(), memberEntity.getTalkId(), memberEntity.getEmailAddress(), memberEntity.getPhoneNumber());
    }
}
