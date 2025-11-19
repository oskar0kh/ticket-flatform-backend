package ticket_flatform.ticket_flatform_spring.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // Entity임을 나타냄 -> DB 테이블과 매핑됨
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // User 클래스의 객체 생성할 때,
                                                   // User 클래스 내부의 'create()' 메서드로만 생성할 수 있도록 보호
@EqualsAndHashCode(of = "userId") // 객체 같은지 비교할 때, "id" 값 만으로 비교
@Table(name = "users") // DB 테이블명을 'users'로 지정
public class User {

    //////////////////////////////////////////////////////////////////
    // DB 테이블 생성 //

    @Id // 이 필드 == private key (PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;     // 각 User 객체들을 구분하는 고유 Id

    @Column(nullable = false, unique = true, length = 20)
    private String username; // 로그인 ID
                             // username VARCHAR(20) NOT NULL UNIQUE

    @Column(nullable = false, length = 20)
    private String password; // 로그인 PW
                             // password VARCHAR(20) NOT NULL

    @Enumerated(EnumType.STRING) // Enum 이름("USER", "ADMIN") -> 문자열로 저장
    @Column(nullable = false)
    private UserRole role; // User 객체들의 role 지정

    //////////////////////////////////////////////////////////////////
    // 비즈니스 로직 (Rich Domain) //

    // User Entity(객체) 생성 메서드 : new User()로 매번 생성하는 대신, 객체 생성을 전담하는 메서드를 만드는 것!

    public static User create(String username, String rawPassword, PasswordEncoder passwordEncoder) {
        User user = new User(); // 1. User 클래스의 'user' 객체 생성
        
        user.username = username; // 2. user 객체의 username, password, role 영역 채워주기

        // 비즈니스 로직1: User 도메인 스스로 비밀번호를 암호화하는 로직
        // (비즈니스 규칙 만족 : 비밀번호는 반드시 암호화 되어야 한다)
        user.password = passwordEncoder.encode(rawPassword);

        // 비즈니스 로직2: 도메인 스스로 기본 역할을 할당하는 로직 (USER, ADMIN)
        // (비즈니스 규칙 만족 : 새로 생성된 사용자는 기본적으로 "USER" 등급을 가진다)
        user.role = UserRole.USER;
        
        return user; // 모든 비즈니스 로직 적용된 user 객체 반환
    }
}
