package project.purelabel.user.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import project.purelabel.user.api.request.customannotation.ListSize;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;
import project.purelabel.user.exception.UserException;
import project.purelabel.user.exception.errorcode.UserExceptionErrorCode;

import java.util.List;

@Getter
public class UserSignUpRequestDto {
    @NotBlank(message = "회원가입 시 이름은 필수입니다.")
    String name;
    @NotBlank(message = "회원가입 시 아이디는 필수입니다.")
    String id;
    @NotBlank(message = "회원가입 시 비밀번호는 필수입니다.")
    String password;
    @NotNull(message = "회원가입 시 성별은 필수입니다.")

    Gender gender;
    @NotNull(message = "회원가입 시 피부타입은 필수입니다.")
    SkinType skinType;
    @ListSize(min = 1, max = 5, message = "피부고민은 1개에서 5개 사이로 입력해야 합니다.")
    List<SkinWorry> skinWorries;

    @Builder
    public UserSignUpRequestDto(String name, String id, String password, Gender gender, SkinType skinType, List<SkinWorry> skinWorries) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.skinType = skinType;
        this.skinWorries = skinWorries;
        verifySkinWorries(skinWorries);
    }

    private void verifySkinWorries(List<SkinWorry> skinWorries) {
        if(skinWorries==null) skinWorries = List.of();
        if(skinWorries.contains(SkinWorry.해당없음))
            if(skinWorries.size()!=1)
                //이 예외는 Handler로 안잡힘... HttpMessageNotReadableException로 감싸지나봄.. 일단 BaseExceptionHandler에서 응급처치함
                throw new UserException(UserExceptionErrorCode.INVALID_SKIN_WORRIES);
    }
}
