package project.purelabel.cosmetic.exception.errorcode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import project.purelabel.global.response.ResponseStatus;

@RequiredArgsConstructor
public enum CosmeticErrorCode implements ResponseStatus {
    /**
     * 5000: Cosmetic 오류
     */
    NO_COSMETIC(5000,HttpStatus.BAD_REQUEST.value(), "존재하지 않는 화장품입니다.");

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
