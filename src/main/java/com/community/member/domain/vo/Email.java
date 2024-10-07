package com.community.member.domain.vo;

import com.community.member.domain.repository.MemberRepository;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    @Column(name = "email", nullable = false)
    private String value;

    public Email(String email) {
        this.value = email;
    }

    public static Email signUp(String email, MemberRepository memberRepository) {
        validateEmail(email);
        checkDuplicateEmail(email, memberRepository);
        return new Email(email);
    }

    public static Email from(String email) {
        validateEmail(email);
        return new Email(email);
    }

    private static void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수입니다.");
        }

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private static void checkDuplicateEmail(String email, MemberRepository memberRepository) {
        if (memberRepository.existsMemberByEmail(new Email(email))) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }
}
