package project.purelabel.cosmetic.exception;

import lombok.Getter;
import project.purelabel.global.response.ResponseStatus;

@Getter
public class CosmeticException extends RuntimeException{
    private final ResponseStatus exceptionStatus;

    public CosmeticException(ResponseStatus exceptionStatus) {
        super(exceptionStatus.getMessage());
        this.exceptionStatus = exceptionStatus;
    }
}
