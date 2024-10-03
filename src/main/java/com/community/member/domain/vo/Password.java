package com.community.member.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]{8,20}$";

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        this.value = value;
    }

    public static Password of(String rawPassword, PasswordEncoder passwordEncoder) {
        validatePassword(rawPassword);
        return new Password(passwordEncoder.encode(rawPassword));
    }

    private static void validatePassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수입니다.");
        }

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (!pattern.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }
}
