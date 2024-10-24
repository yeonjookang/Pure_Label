package project.purelabel.user.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.domain.entity.User;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    @DisplayName("피부고민 리스트는 데이터베이스에 하나의 문자열로 콤마로 연결되어 저장된다.")
    void 피부고민_리스트_문자열_변환(){
        //given
        String name = "name";
        String id = "id";
        String password = "password";
        Gender gender = Gender.남성;
        SkinType skinType = SkinType.중성;
        List<SkinWorry> skinWorries = List.of(SkinWorry.각질,SkinWorry.미백_잡티);

        UserSignUpRequestDto requestDto = UserSignUpRequestDto.builder()
                .name(name)
                .id(id)
                .password(password)
                .gender(gender)
                .skinType(skinType)
                .skinWorries(skinWorries)
                .build();

        User user = User.builder().signUpRequestDto(requestDto).build();

        //when
        Long savedPk = userRepository.save(user).getPk();

        //then
        Optional<User> findUser = userRepository.findByPk(savedPk);
        Assertions.assertThat(findUser.get().getSkinWorries())
                .isEqualTo(skinWorries);
    }
}