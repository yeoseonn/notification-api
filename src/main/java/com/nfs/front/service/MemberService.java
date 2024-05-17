package com.nfs.front.service;

import com.nfs.front.entity.MemberEntity;
import com.nfs.front.error.ApiFailedException;
import com.nfs.front.error.ErrorCode;
import com.nfs.front.model.Member;
import com.nfs.front.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getMemberById(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElse(null);
        if (Objects.isNull(member)) {
            throw new ApiFailedException(ErrorCode.USER_INVALID_ERROR, "cannot find member Id : " + memberId);
        }
        return Member.of(member);
    }
}
