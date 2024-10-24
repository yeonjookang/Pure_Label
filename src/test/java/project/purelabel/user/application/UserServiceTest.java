package project.purelabel.user.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.purelabel.global.jwt.JwtParser;
import project.purelabel.user.api.request.UserLoginRequestDto;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.application.response.UserLoginResponseDto;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;
import project.purelabel.user.exception.UserException;
import project.purelabel.user.exception.errorcode.UserExceptionErrorCode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtParser jwtParser;

    @DisplayName("중복된 아이디면 예외가 발생한다.")
    @Test
    void 회원가입_아이디_중복_예외(){
        //given
        String id = "duplicateId";
        String password = "userPassword";
        Gender gender = Gender.남성;
        SkinType skinType = SkinType.중성;
        List<SkinWorry> skinWorries = List.of(SkinWorry.각질, SkinWorry.미백_잡티, SkinWorry.모공, SkinWorry.주름_탄력, SkinWorry.속건조);

        UserSignUpRequestDto signUpRequest = UserSignUpRequestDto.builder()
                .id(id)
                .password(password)
                .gender(gender)
                .skinType(skinType)
                .skinWorries(skinWorries)
                .build();

        //when & then
        assertThatThrownBy(()->userService.signUp(signUpRequest))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionErrorCode.DUPLICATE_ID.getMessage());
    }

    @DisplayName("비밀번호가 틀리면 예외가 발생한다.")
    @Test
    void 로그인_비밀번호_틀림_예외(){
        //given
        String id = "userId";
        String password = "weird_password";

        UserLoginRequestDto loginRequest = UserLoginRequestDto.builder()
                .id(id)
                .password(password)
                .build();

        //when, then
        assertThatThrownBy(()->userService.login(loginRequest))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionErrorCode.INVALID_PASSWORD.getMessage());
    }

    @DisplayName("로그인에 성공하면 JWT를 응답한다.")
    @Test
    void 로그인_성공_토큰_응답(){
        //given
        String id = "userId";
        String password = "userPassword";

        UserLoginRequestDto loginRequest = UserLoginRequestDto.builder()
                .id(id)
                .password(password)
                .build();

        //when
        UserLoginResponseDto loginResponse = userService.login(loginRequest);

        //then
        String accessToken = loginResponse.getAccessToken();
        Long userPkFromToken = jwtParser.getUserPkFromToken(accessToken);
        Assertions.assertThat(userPkFromToken)
                .isEqualTo(2L);
    }
}
