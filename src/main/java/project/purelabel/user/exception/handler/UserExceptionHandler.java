package project.purelabel.user.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.purelabel.global.response.BaseErrorResponse;
import project.purelabel.user.exception.UserException;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserException.class)
    public BaseErrorResponse handleUserException(UserException e) {
        log.error("[UserException: handle_UserException 호출]", e);
        return new BaseErrorResponse(e.getExceptionStatus(), e.getMessage());
    }
}