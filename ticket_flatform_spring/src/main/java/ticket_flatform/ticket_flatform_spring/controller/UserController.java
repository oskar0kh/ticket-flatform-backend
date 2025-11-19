package ticket_flatform.ticket_flatform_spring.controller;

import ticket_flatform.ticket_flatform_spring.dto.UserSignUpRequestDto;
import ticket_flatform.ticket_flatform_spring.dto.UserSignUpResponseDto;
import ticket_flatform.ticket_flatform_spring.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController               // 현재 클래스 == JSON 데이터 주고받는 'REST API용 컨트롤러' 라고 선언
@RequiredArgsConstructor      // final 붙은 필드에 객체 자동으로 주입해줌 (DI)
@RequestMapping("/api/users") // 이 클래스 안의 모든 기능은 "/api/users" URL로 시작함
public class UserController {

    // Container에서 userService 객체 가져오기 (DI)
    private final UserService userService;

    @PostMapping("/signup") // 'POST /api/users/signup' 으로 들어오는 HTTP Request 받는 메서드
    public ResponseEntity<UserSignUpResponseDto> signUp( @Valid @RequestBody UserSignUpRequestDto requestDto
    ) {
        // Request DTO 객체 -> Service에게 전달 -> Service의 반환값 받기 (Response DTO 객체)
        UserSignUpResponseDto responseDto = userService.signUp(requestDto);

        // ResponseEntity에 'Response DTO + HTTP 201 Created' 묶어서 저장 -> ResponseEntity 반환 (성공 응답)
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}