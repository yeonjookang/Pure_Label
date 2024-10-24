package project.purelabel.user.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.domain.SkinWorryListConverter;
import project.purelabel.user.domain.value.Gender;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    @NotNull
    private String name;
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull
    @Enumerated(EnumType.STRING)
    private SkinType skinType;
    @Convert(converter = SkinWorryListConverter.class)
    @Column(name = "skin_worries")
    private List<SkinWorry> skinWorries;

    @Builder
    public User(UserSignUpRequestDto signUpRequestDto) {
        this.name = signUpRequestDto.getName();
        this.id = signUpRequestDto.getId();
        this.password = signUpRequestDto.getPassword();
        this.gender = signUpRequestDto.getGender();
        this.skinType = signUpRequestDto.getSkinType();
        this.skinWorries = signUpRequestDto.getSkinWorries();
    }
}
