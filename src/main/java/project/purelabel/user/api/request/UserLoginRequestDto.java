package project.purelabel.user.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserLoginRequestDto {
    @NotBlank(message = "로그인 시 아이디는 필수입니다.")
    String id;
    @NotBlank(message = "로그인 시 비밀번호는 필수입니다.")
    String password;
}
