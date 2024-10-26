package project.purelabel.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.purelabel.global.response.BaseResponse;
import project.purelabel.user.api.request.UserInfoRequestDto;
import project.purelabel.user.api.request.UserLoginRequestDto;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.application.UserService;
import project.purelabel.user.application.response.UserInfoResponseDto;
import project.purelabel.user.application.response.UserLoginResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public BaseResponse<UserLoginResponseDto> login(@Validated @RequestBody UserLoginRequestDto loginRequest ) {
        UserLoginResponseDto loginResponse = userService.login(loginRequest);
        return new BaseResponse<>(loginResponse);
    }

    @PostMapping("/signUp")
    public BaseResponse signUp(@Validated @RequestBody UserSignUpRequestDto signUpRequest ) {
        userService.signUp(signUpRequest);
        return new BaseResponse<>();
    }

    public BaseResponse<UserInfoResponseDto> getUserInfo(@Validated @RequestBody UserInfoRequestDto userInfoRequestDto){
        UserInfoResponseDto response = userService.getUserInfo(userInfoRequestDto.getPk());
        return new BaseResponse<>(response);
    }
}
