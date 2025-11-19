package ticket_flatform.ticket_flatform_spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 이 클래스 == Configuration 클래스임을 선언 (내부에 @Bean 달린 것들을 전부 Container에 미리 객체로 등록시킴)
@EnableWebSecurity
public class SecurityConfig {

    // 1. PasswordEncoder Bean(객체) -> Container에 등록
    //  -> User, UserService 클래스에서 해당 객체 뽑아서 사용 가능 (Bean이 다른 클래스들로 Injection됨)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder() 객체 -> Spring Bean으로 등록
    }

    // 2. SecurityFilterChain 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    // 보안 규칙들 (filter)
                .csrf(csrf -> csrf.disable()) // 1. csrf 비활성화 (stateless -> csrf 비활성화 시킴)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authz -> authz // 주소 검사
                        .requestMatchers("/api/users/signup").permitAll() // 1) 회원가입 경로는 모두 접근 가능
                        .anyRequest().authenticated() // 2) 나머지 경로는 인증된 사용자만 접근 가능
                );
        return http.build();
    }
}
