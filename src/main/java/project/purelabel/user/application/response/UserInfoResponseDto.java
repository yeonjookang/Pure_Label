package project.purelabel.user.application.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponseDto {
    private String name;
    private String id;
    private Gender gender;
    private SkinType skinType;
    private List<SkinWorry> skinWorries;
}
