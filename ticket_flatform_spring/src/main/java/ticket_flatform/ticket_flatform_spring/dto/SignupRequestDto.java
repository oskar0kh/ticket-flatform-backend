package ticket_flatform.ticket_flatform_spring.dto;

import lombok.Getter;
import lombok.Setter;

// Lombok 어노테이션을 사용하여 Getter/Setter를 자동으로 생성
@Getter
@Setter
public class SignupRequestDto {
    private String username; // JSON의 'username' Key와 이 필드명이 일치해야 함
    private String password; // JSON의 'password' Key와 이 필드명이 일치해야 함
}