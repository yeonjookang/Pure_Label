package project.purelabel.global.exception.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import project.purelabel.global.response.BaseErrorResponse;
import project.purelabel.user.exception.errorcode.UserExceptionErrorCode;

import java.util.stream.Collectors;

import static project.purelabel.global.exception.errorcode.BaseExceptionErrorCode.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class BaseExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handle_MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[BaseExceptionControllerAdvice: handle_MethodArgumentNotValidException 호출]", e);

        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new BaseErrorResponse(BAD_REQUEST,errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseErrorResponse handle_HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        //Spring Boot에서는 InvalidDefinitionException이 발생하면, 이 예외가 상위 예외인 HttpMessageNotReadableException으로 감싸져서 처리된다.
        log.error("[BaseExceptionControllerAdvice: handle_HttpMessageNotReadableException 호출]", e);
        Throwable cause = e.getCause();

        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;

            Class<?> targetType = ife.getTargetType();

            if (targetType.isEnum()) {
                String enumClassName = targetType.getSimpleName();

                String errorMessage = String.format("%s: 유효하지 않은 %s 값입니다.", enumClassName,enumClassName);

                return new BaseErrorResponse(BAD_REQUEST,errorMessage);
            }
        }
        if (cause instanceof ValueInstantiationException) {
            return new BaseErrorResponse(UserExceptionErrorCode.INVALID_SKIN_WORRIES);
        }
        return new BaseErrorResponse(BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseErrorResponse handle_MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[BaseExceptionControllerAdvice: handle_HttpMessageNotReadableException 호출]", e);
        String errorMessage = e.getMessage();
        return new BaseErrorResponse(BAD_REQUEST,errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseErrorResponse handle_IllegalArgumentException(IllegalArgumentException e) {
        log.error("[BaseExceptionControllerAdvice: handle_IllegalArgumentException 호출]", e);

        String errorMessage = e.getMessage();

        return new BaseErrorResponse(BAD_REQUEST,errorMessage);
    }
}
