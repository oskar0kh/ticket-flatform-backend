package ticket_flatform.ticket_flatform_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // UserSignUpRequestDto의 깡통 객체 생성 -> dto 객체에 username, password값 저장하고, 상대편에게 전달
public class UserSignUpRequestDto {

    @NotBlank(message = "ID는 필수 입력 항목입니다.")
    @Size(min = 4, max = 20, message = "ID는 4자 이상 20자 이하로 입력해주세요.")
    private String username; // 'email' -> 'username'

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 8, message = "비밀번호는 8자 이상 20자 이하여야 합니다.")
    private String password;
}