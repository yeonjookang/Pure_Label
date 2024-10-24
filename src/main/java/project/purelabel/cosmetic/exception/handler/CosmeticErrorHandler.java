package project.purelabel.cosmetic.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.purelabel.cosmetic.exception.CosmeticException;
import project.purelabel.global.response.BaseErrorResponse;

@Slf4j
@RestControllerAdvice
public class CosmeticErrorHandler {
    @ExceptionHandler(CosmeticException.class)
    public BaseErrorResponse handleUserException(CosmeticException e) {
        log.error("[UserException: handle_UserException 호출]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}
