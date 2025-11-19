package ticket_flatform.ticket_flatform_spring.service;

import ticket_flatform.ticket_flatform_spring.domain.User;
import ticket_flatform.ticket_flatform_spring.dto.UserSignUpRequestDto;
import ticket_flatform.ticket_flatform_spring.dto.UserSignUpResponseDto;
import ticket_flatform.ticket_flatform_spring.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // UserService 클래스 -> Spring에 'Service' 역할 Bean으로 등록
@RequiredArgsConstructor // final 붙은 필드에 객체를 자동으로 주입해줌 (DI)
@Transactional(readOnly = true) // 이 클래스 안의 모든 메소드는 한번에 수행되어야 함 & 읽기 전용으로만 실행됨
public class UserService {

    // Container에서 userRepository, passwordEncoder 객체 가져옴 (DI)
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    ///////////////////////////////////////////////////////////////
    // 회원가입 기능 //
    ///////////////////////////////////////////////////////////////

    @Transactional // 회원가입은 DB에 데이터 쓰기(Write) 작업 필요 -> 따로 Transaction 묶기
    public UserSignUpResponseDto signUp(UserSignUpRequestDto requestDto) {
        // 1. ID 중복 확인 (Repository 사용)
        //    : Request DTO에서, 사용자가 입력한 username 꺼내기 -> DB속 username들과 같은거 있는지 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) { // 'existsByEmail' -> 'existsByUsername'
            throw new IllegalArgumentException("이미 사용 중인 ID입니다.");
        }

        // 2. 새로운 사용자 (Domain 객체) 생성
        //    : Service는 암호화 로직을 모름
        //    -> User 도메인에 'username', 'rawPassword', 'passwordEncoder'를 전달하고,
        //       User.java의 'create()' 메서드로 Domain 객체 생성
        User newUser = User.create(
                requestDto.getUsername(), // 'getEmail' -> 'getUsername'
                requestDto.getPassword(),
                passwordEncoder
        );

        // 3. 새로운 사용자 저장 (Repository 메서드 사용 -> DB에 저장)
        User savedUser = userRepository.save(newUser);

        // 4. 새로운 사용자 -> Response DTO로 변환 -> Controller에게 반환
        //    : 어떤 사용자가 추가됐는지, Client에게 알려주기 위함
        return new UserSignUpResponseDto(savedUser);
    }
}