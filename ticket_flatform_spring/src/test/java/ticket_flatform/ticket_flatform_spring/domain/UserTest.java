package ticket_flatform.ticket_flatform_spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) // Mockito 라이브러리 사용 (@Mock 기능 사용)
class UserTest {

    @Mock
    private PasswordEncoder passwordEncoder; // 가짜 객체 생성 (passwordEncoder)
                                             // -> User.create() 메서드가 잘 동작하는지 확인하는 용도

    @Test
    @DisplayName("User.create() 메서드 테스트")
    void createUserTest() {
        // 1. given (입력값, 예상 반환값, 반환 로직 설정)
        String username = "testUser";
        String rawPassword = "password123";
        String encodedPassword = "encoded_password_abc";

        // Mockito 설정: "password123"이 들어오면 "encoded_password_abc"를 반환
        given(passwordEncoder.encode(rawPassword)).willReturn(encodedPassword);

        // 2. when (실제 메서드 실행)
        // 실제 'User.create' 메서드 호출 : passwordEncoder 부분에, 위에서 @Mock 부분에 정의한 가짜 passwordEncoder 전달
        
        // User.create() 내부의 'user.password = passwordEncoder.encode(rawPassword);' 부분에서,
        // passwordEncoder.encode(rawPassword)를 실행할 때, Mockito가 결과값 가로채서 "encoded_password_abc" 반환
        User user = User.create(username, rawPassword, passwordEncoder);

        // 3. then ('실제 메서드 결과값 == 예상 반환값' 맞는지 비교)
        // 1) username이 올바르게 설정되었는지 검증
        assertThat(user.getUsername()).isEqualTo(username);

        // 2) [중요] 비밀번호가 Mockito가 반환하기로 한 값과 일치하는지 검증
        assertThat(user.getPassword()).isEqualTo(encodedPassword);

        // 3) [중요] 역할(Role)이 'USER'로 올바르게 설정되었는지 검증
        assertThat(user.getRole()).isEqualTo(UserRole.USER);
    }
}