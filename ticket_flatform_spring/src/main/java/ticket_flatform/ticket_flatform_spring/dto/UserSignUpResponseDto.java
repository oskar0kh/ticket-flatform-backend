package ticket_flatform.ticket_flatform_spring.dto;

import ticket_flatform.ticket_flatform_spring.domain.User;
import lombok.Getter;

// Response DTO : 회원가입 끝나고, DB에 저장된 Entity 중 필요한 정보들만 Response DTO에 저장 -> Client에게 반환

@Getter // userId, username에 대하여, 'getUserId(), getUsername()' 메서드를 자동으로 생성
        // * [2단계] UserSignUpResponseDto의 Getter 사용 -> DTO 객체에 저장된 값들 꺼내서, JSON 형식으로 변환할때 사용
public class UserSignUpResponseDto {

    // Client에게 공개할(전달할) 데이터들 종류
    private Long userId;
    private String username;

    // 생성자 실행 : DB 속 'User' Entity -> DTO에 복사, 저장 (password 같은 민감한 정보는 DTO에 안넣음)
    // * [1단계] user 객체의 Getter 사용 -> Entity에서 데이터 꺼내서, DTO에 저장
    public UserSignUpResponseDto(User user) {
        this.userId = user.getUserId(); // 'user'에서 'userId' 가져옴 -> DTO 객체(this)의 'userId' 칸에 복사
        this.username = user.getUsername(); // 'user'에서 'username' 가져옴 -> DTO 객체(this)의 'username' 칸에 복사
    }
}