package project.purelabel.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.purelabel.global.jwt.JwtTokenProvider;
import project.purelabel.user.api.request.UserLoginRequestDto;
import project.purelabel.user.api.request.UserSignUpRequestDto;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
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

    private UserLoginResponseDto generateResponseWithToken(User user) {
        String accessToken = jwtTokenProvider.createAccessToken(user.getPk());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getPk());
        redisTemplate.opsForValue().set(user.getPk().toString(),refreshToken);
        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private boolean isDuplicateId(String id) {
        return userRepository.existsById(id);
    }
}
