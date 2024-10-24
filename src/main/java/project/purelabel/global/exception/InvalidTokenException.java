package project.purelabel.global.exception;

import lombok.Getter;
import project.purelabel.global.response.ResponseStatus;

@Getter
public class InvalidTokenException extends RuntimeException {
    private final ResponseStatus exceptionStatus;

    public InvalidTokenException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.exceptionStatus = responseStatus;
    }

    public InvalidTokenException(ResponseStatus responseStatus, String message) {
        super(message);
        this.exceptionStatus = responseStatus;
    }
}