package com.community.member.domain;

import com.community.member.domain.repository.MemberRepository;
import com.community.member.domain.vo.Email;
import com.community.member.repository.InMemoryMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {

    MemberRepository memberRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
    }

    @Test
    @DisplayName("유효한 이메일로 Email 객체 생성 성공")
    void createEmail_success() {
        String validEmail = "test@gmail.com";

        Email response = Email.of(validEmail, memberRepository);

        assertThat(response).isNotNull();
        assertThat(response.getValue()).isEqualTo(validEmail);
    }

    @Test
    @DisplayName("입력값이 null인 경우 예외 발생")
    void createEmail_nullEmail() {
        String nullEmail = null;

        assertThatThrownBy(() -> Email.of(nullEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 빈 문자열인 경우 예외 발생")
    void createEmail_emptyEmail() {
        String emptyEmail = "";

        assertThatThrownBy(() -> Email.of(emptyEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생")
    void createEmail_invalidEmail() {
        String invalidEmail = "@.";

        assertThatThrownBy(() -> Email.of(invalidEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생2")
    void createEmail_invalidEmail2() {
        String invalidEmail = "invalid@email";

        assertThatThrownBy(() -> Email.of(invalidEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 잘못된 형식인 경우 예외 발생3")
    void createEmail_invalidEmail3() {
        String invalidEmail = "invalid-email";

        assertThatThrownBy(() -> Email.of(invalidEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("중복된 이메일로 Email 객체 생성시 예외")
    void createEmail_duplicateEmail() {
        String existedEmail = "test@gmail.com";

        Member member = Member.of(existedEmail, "testPassword", passwordEncoder, memberRepository);
        memberRepository.save(member);

        assertThatThrownBy(() -> Email.of(existedEmail, memberRepository))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
