package com.community.member.domain;

import com.community.member.domain.vo.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {

    @Test
    @DisplayName("유효한 이메일로 Email 객체 생성 성공")
    void createEmail_success() {
        String validEmail = "test@gmail.com";

        Email response = Email.of(validEmail);

        assertThat(response).isNotNull();
        assertThat(response.getValue()).isEqualTo(validEmail);
    }

    @Test
    @DisplayName("입력값이 null인 경우 예외 발생")
    void createEmail_nullEmail() {
        String nullEmail = null;

        assertThatThrownBy(() -> Email.of(nullEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 빈 문자열인 경우 예외 발생")
    void createEmail_emptyEmail() {
        String emptyEmail = "";

        assertThatThrownBy(() -> Email.of(emptyEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생")
    void createEmail_invalidEmail() {
        String invalidEmail = "@.";

        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생2")
    void createEmail_invalidEmail2() {
        String invalidEmail = "invalid@email";

        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생3")
    void createEmail_invalidEmail3() {
        String invalidEmail = "invalid-email";

        assertThatThrownBy(() -> Email.of(invalidEmail))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
