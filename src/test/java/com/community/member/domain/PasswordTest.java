package com.community.member.domain;

import com.community.member.domain.vo.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    @DisplayName("유효한 비밀번호로 Password 객체 생성 완료")
    void passwordCreate_success() {
        String rawPassword = "valid-String";

        Password password = Password.of(rawPassword, passwordEncoder);

        assertNotNull(password);
        assertNotEquals(rawPassword, password.getValue());
        assertTrue(passwordEncoder.matches(rawPassword, password.getValue()));
    }

    @Test
    @DisplayName("특수문자가 포함된 유효한 비밀번호로 Password 객체 생성 완료")
    void passwordCreate_success_special() {
        String rawPassword = "@String,.,!@#";

        Password password = Password.of(rawPassword, passwordEncoder);

        assertNotNull(password);
        assertNotEquals(rawPassword, password.getValue());
        assertTrue(passwordEncoder.matches(rawPassword, password.getValue()));
    }

    @Test
    @DisplayName("비밀번호가 null인 경우 예외 발생")
    void passwordCreate_nullPassword() {
        String rawPassword = null;

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 짧은 경우 예외 발생")
    void passwordCreate_shortPassword() {
        String rawPassword = "short";

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 긴 경우 예외 발생")
    void passwordCreate_longPassword() {
        String rawPassword = "long-password-1231231231231212312312123123123123";

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 비어있는 문자열인 경우 예외 발생")
    void passwordCreate_emptyPassword() {
        String rawPassword = "";

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 공백만 8번일 경우 예외 발생")
    void passwordCreate_blankPassword() {
        String rawPassword = "        ";

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호가 공백 포함 8자일 경우 예외 발생")
    void passwordCreate_invalidPassword() {
        String rawPassword = "p ssword";

        assertThatThrownBy(() -> Password.of(rawPassword, passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
