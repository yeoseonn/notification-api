package com.nfs.front.service;

import com.nfs.front.entity.MemberEntity;
import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import com.nfs.front.model.Member;
import com.nfs.front.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    MemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        memberService = new MemberService(memberRepository);
    }

    @Test
    public void findByPhone() {
        // given
        MemberEntity memberEntity = new MemberEntity(1004L, "0102222333", "yeoseon.yun@kakao.com", "test", LocalDateTime.now());
        when(memberRepository.findById(memberEntity.getMemberId())).thenReturn(Optional.of(memberEntity));
        // When
        Member member = memberService.getMemberById(memberEntity.getMemberId());
        // Then
        assertEquals(member.memberId(), memberEntity.getMemberId());
        assertEquals(member.phoneNumber(), memberEntity.getPhoneNumber());
    }

    @Test
    public void findByPhone_throwError() {
        // given
        Long memberId = 111L;
        // when,then
        ApiFailedException exception = assertThrows(ApiFailedException.class, () -> memberService.getMemberById(memberId));

        assertEquals(ErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
    }
}