package project.purelabel.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.purelabel.user.api.request.UserLoginRequestDto;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.application.UserService;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.purelabel.global.exception.errorcode.BaseExceptionErrorCode.BAD_REQUEST;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {
    /**
     * Controller 테스트 방법
     * => 요청이 잘못되었을 때, 예외가 발생하는지,
     * => 예외가 발생했을 때, 내가 의도한 응답이 나가는지
     * 두 가지 경우로 나눠서 테스트했다!
     * 응답만 테스트하면, 응답에서 오류가 났을 때, 요청에서 잘 못잡은건지, 커스텀 응답이 안나가는 건지 모르기 때문!
     */
    private Validator validator;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    //UserController에 UserService 필드를 추가하니까 오류가 남. MockBean으로 의존성 주입을 해줘야함.
    private UserService userService;

    @BeforeEach
    void setValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * 로그인, 회원가입 시 요청 검증 테스트
     * - 입력 정보 모두 필수
     * - SkinWorries는 1개~5개 제한
     * - 성별, 피부타입, 피부고민은 Enum에 정의된 문자열만 가능
     */

    @DisplayName("로그인 시 아이디와 비밀번호가 null이면 검증기에 잡힌다.")
    @Test
    void 로그인_아이디_비밀번호_예외() {
        //given
        String id = null;
        String password = null;

        UserLoginRequestDto user = UserLoginRequestDto.builder()
                .id(id)
                .password(password)
                .build();
        //when
        Set<ConstraintViolation<UserLoginRequestDto>> violations = validator.validate(user);
        //then
        Assertions.assertEquals(violations.size(), 2);
    }

    @DisplayName("회원가입 시 입력 정보들 중 하나라도 입력 안하면 검증기에 잡힌다.")
    @Test
    void 회원가입_입력정보_예외() {
        //given
        String name = " ";
        String id = " ";
        String password = " ";
        Gender gender = null;
        SkinType skinType = null;
        List<SkinWorry> skinWorries = null;

        UserSignUpRequestDto user = UserSignUpRequestDto.builder()
                .name(name)
                .id(id)
                .password(password)
                .gender(gender)
                .skinType(skinType)
                .skinWorries(skinWorries)
                .build();

        //when
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(user);

        //then
        Assertions.assertEquals(violations.size(), 6);
    }

    @DisplayName("회원가입 시 피부 타입이 6개이면 검증기에 잡힌다.")
    @Test
    void 회원가입_피부타입_갯수_예외() {
        //given
        String name = "userName";
        String id = "userId";
        String password = "userPassword";
        Gender gender = Gender.남성;
        SkinType skinType = SkinType.중성;
        List<SkinWorry> skinWorries = List.of(SkinWorry.각질, SkinWorry.미백, SkinWorry.모공, SkinWorry.주름, SkinWorry.속건조, SkinWorry.다크서클);

        UserSignUpRequestDto user = UserSignUpRequestDto.builder()
                .name(name)
                .id(id)
                .password(password)
                .gender(gender)
                .skinType(skinType)
                .skinWorries(skinWorries)
                .build();

        //when
        Set<ConstraintViolation<UserSignUpRequestDto>> violations = validator.validate(user);

        //then
        Assertions.assertEquals(violations.size(), 1);
    }

    @DisplayName("회원가입 시 Enum 클래스에 정의안된 문자열이 들어오면 InvalidDefinitionException이 발생한다.")
    @Test
    void 회원가입_성별_예외() {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = "{\n" +
                "    \"name\": \"userName\",\n" +
                "    \"id\": \"userId\",\n" +
                "    \"password\": \"userPassword\",\n" +
                "    \"gender\": \"weired_gender\",\n" +
                "    \"skinType\": \"중성\",\n" +
                "    \"skinWorries\": [\"각질\", \"미백_잡티\", \"모공\", \"주름_탄력\", \"속건조\", \"다크서클\"]\n" +
                "}";

        // when
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            objectMapper.readValue(jsonRequest, UserSignUpRequestDto.class);
        });

        // then
        Assertions.assertTrue(exception instanceof InvalidDefinitionException
                || exception.getCause() instanceof IllegalArgumentException);
    }

    @DisplayName("회원가입 시 피부 고민에 해당없음과 다른 값이 함께 들어오면 InvalidDefinitionException이 발생한다.")
    @Test
    void 회원가입_피부고민_해당없음_예외() {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = "{\n" +
                "    \"name\": \"userName\",\n" +
                "    \"id\": \"userId\",\n" +
                "    \"password\": \"userPassword\",\n" +
                "    \"gender\": \"남성\",\n" +
                "    \"skinType\": \"중성\",\n" +
                "    \"skinWorries\": [\"각질\", \"해당없음\"]\n" +
                "}";

        // when
        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            objectMapper.readValue(jsonRequest, UserSignUpRequestDto.class);
        });

        // then
        Assertions.assertTrue(exception instanceof InvalidDefinitionException);
    }

    /**
     * 로그인, 회원가입 시 응답 테스트
     * - handler에서 예외를 잡아서 의도한 예외 응답을 잘보내는지
     */

    @Test
    @DisplayName("로그인 시 검증기에 잡힌 예외는 커스텀한 응답이 반환된다.")
    void 로그인_예외_응답() throws Exception {
        //given
        String requestBody = "{\n" +
                "    \"id\": \" \",\n" +
                "    \"password\": \" \"}";

        //when, then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value("password: 로그인 시 비밀번호는 필수입니다., id: 로그인 시 아이디는 필수입니다."));
    }

    @DisplayName("회원가입 시 피부 타입이 6개이면 커스텀한 응답이 반환된다.")
    @Test
    void 회원가입_피부타입_갯수_예외_응답() throws Exception {
        //given
        String requestBody = "{\n" +
                "    \"name\": \"userName\",\n" +
                "    \"id\": \"userId\",\n" +
                "    \"password\": \"userPassword\",\n" +
                "    \"gender\": \"여성\",\n" +
                "    \"skinType\": \"중성\",\n" +
                "    \"skinWorries\": [\"각질\", \"미백_잡티\", \"모공\", \"주름_탄력\", \"속건조\", \"다크서클\"]\n" +
                "}";

        //when, then
        mockMvc.perform(post("/user/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value("skinWorries: 피부고민은 1개에서 5개 사이로 입력해야 합니다."));
    }

    @DisplayName("회원가입 시 Enum 클래스에 정의안된 문자열이 들어오면 커스텀한 응답이 반환된다.")
    @Test
    void 회원가입_성별_예외_응답() throws Exception {
        // given
        String requestBody = "{\n" +
                "    \"name\": \"userName\",\n" +
                "    \"id\": \"userId\",\n" +
                "    \"password\": \"userPassword\",\n" +
                "    \"gender\": \"weired_gender\",\n" +
                "    \"skinType\": \"중성\",\n" +
                "    \"skinWorries\": [\"각질\", \"미백_잡티\", \"모공\", \"주름_탄력\", \"속건조\"]\n" +
                "}";

        //when, then
        mockMvc.perform(post("/user/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value("Gender: 유효하지 않은 Gender 값입니다."));
    }
}

