package ticket_flatform.ticket_flatform_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화 메서드
        return new BCryptPasswordEncoder();
    }

    @Bean // 모든 Http Request 검사하는 규칙(Filter)들 모아놓은 메서드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // http 객체의 CSRF 방어 기능 비활성화

                .authorizeHttpRequests(authorize -> authorize // Http Request의 권한 설정
                        .requestMatchers("/api/auth/**").permitAll() // 회원가입, 로그인 경로로 들어오는 Request : 허용
                        .requestMatchers("/", "/index.html").permitAll() // 메인 페이지로 가는 Request : 허용
                        .anyRequest().authenticated() // 그 외 모든 Request들 : 인증된 사용자(로그인 된 사용자)만 접근 허용
                );

        return http.build(); // 모든 규칙들 모아서 http 객체 생성 & 반환
    }
}
