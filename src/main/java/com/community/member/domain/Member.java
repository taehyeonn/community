package com.community.member.domain;

import com.community.global.base.BaseEntity;
import com.community.member.domain.vo.Email;
import com.community.member.domain.vo.Password;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    public Member(Long id, Email email, Password password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(createdAt, updatedAt);
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member of(String email, String password, PasswordEncoder passwordEncoder) {
        return new Member(
                null,
                Email.of(email),
                Password.of(password, passwordEncoder),
                LocalDateTime.now(),
                null);
    }
}
