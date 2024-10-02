package com.community.member.domain;

import com.community.global.base.BaseEntity;
import com.community.member.domain.vo.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    public Member(Long id, Email email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.email = email;
    }

    public static Member of(String email) {
        return new Member(
                null,
                Email.of(email),
                LocalDateTime.now(),
                null);
    }
}
