package project.purelabel.user.exception;

import lombok.Getter;
import project.purelabel.global.response.ResponseStatus;

@Getter
public class UserException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public UserException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}