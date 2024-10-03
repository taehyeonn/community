package com.community.member.service.dto;

import com.community.member.domain.Member;

public record MemberDto(Long id, String email) {

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getEmail().getValue());
    }
}
