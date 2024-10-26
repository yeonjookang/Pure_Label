package project.purelabel.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.purelabel.user.api.request.UserLoginRequestDto;
import project.purelabel.user.api.request.UserSignUpRequestDto;
import project.purelabel.user.application.response.UserInfoResponseDto;
import project.purelabel.user.application.response.UserLoginResponseDto;
import project.purelabel.user.domain.UserRepository;
import project.purelabel.user.domain.entity.User;
import project.purelabel.user.exception.UserException;
import project.purelabel.user.exception.errorcode.UserExceptionErrorCode;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void signUp(UserSignUpRequestDto signUpRequestDto) {
        if(isDuplicateId(signUpRequestDto.getId()))
            throw new UserException(UserExceptionErrorCode.DUPLICATE_ID);
        User user = User.builder().signUpRequestDto(signUpRequestDto).build();
        userRepository.save(user);
    }

    public UserLoginResponseDto login(UserLoginRequestDto loginRequest) {
        Optional<User> findUser = userRepository.findById(loginRequest.getId());
        if(!findUser.isPresent())
            throw new UserException(UserExceptionErrorCode.NO_EXIST_USER);
        if(!findUser.get().getPassword().equals(loginRequest.getPassword()))
            throw new UserException(UserExceptionErrorCode.INVALID_PASSWORD);
        UserLoginResponseDto loginResponse = generateResponseWithToken(findUser.get());
        return loginResponse;
    }

    public UserInfoResponseDto getUserInfo(Long pk) {
        Optional<User> findUser = userRepository.findByPk(pk);
        if(!findUser.isPresent())
            throw new UserException(UserExceptionErrorCode.NO_EXIST_USER);

        return UserInfoResponseDto.builder()
                .name(findUser.get().getName())
                .id(findUser.get().getId())
                .gender(findUser.get().getGender())
                .skinType(findUser.get().getSkinType())
                .skinWorries(findUser.get().getSkinWorries())
                .build();
    }

    private UserLoginResponseDto generateResponseWithToken(User user) {
        String accessToken = "skdjfiegknaskdjfioagksdljgsikjfidosjdkjgisodgjsklg";
        String refreshToken = "aiogjisjgioajiojfidjsgksandkgnaslidngksdglsgsdgsd";
        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isDuplicateId(String id) {
        return userRepository.existsById(id);
    }
}
