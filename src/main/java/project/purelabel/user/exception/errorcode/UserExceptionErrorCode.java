package project.purelabel.user.exception.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import project.purelabel.global.response.ResponseStatus;

@RequiredArgsConstructor
public enum UserExceptionErrorCode implements ResponseStatus {

    /**
     * 3000: User 오류
     */
    DUPLICATE_ID(3000, HttpStatus.BAD_REQUEST.value(), "중복된 아이디입니다."),
    NO_EXIST_USER(3001, HttpStatus.BAD_REQUEST.value(), "존재하지 않은 사용자 정보입니다."),
    INVALID_PASSWORD(3002, HttpStatus.BAD_REQUEST.value(), "비밀번호가 틀렸습니다."),
    INVALID_SKIN_WORRIES(3003, HttpStatus.BAD_REQUEST.value(), "해당없음은 다른 피부고민과 중복될 수 없습니다.");

    private final int code;
    private final int status;
    private final String message;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}


