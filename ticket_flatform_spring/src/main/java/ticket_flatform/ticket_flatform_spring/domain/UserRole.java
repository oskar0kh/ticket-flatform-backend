package ticket_flatform.ticket_flatform_spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // private한 'key 필드값' 가져오는 'getKey()' 메서드 자동으로 생성해줌
@RequiredArgsConstructor // final로 선언한 변수들에 대한 생성자를 자동으로 생성해줌
public enum UserRole {
    USER("ROLE_USER"),   // USER 객체 생성될 때, 객체 내부의 key값은 "ROLE_USER"로 초기화됨
    ADMIN("ROLE_ADMIN"); // ADMIN 객체 생성될 때, 객체 내부의 key값은 'ROLE_ADMIN"으로 초기화됨

    private final String key;
}