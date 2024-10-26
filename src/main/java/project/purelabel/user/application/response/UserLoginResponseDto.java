package project.purelabel.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserLoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long pk;
}
