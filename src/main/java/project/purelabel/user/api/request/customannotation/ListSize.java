package project.purelabel.user.api.request.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ListSizeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ListSize {
    String message() default "목록의 항목 수는 1개에서 5개 사이여야 합니다.";

    int min() default 1;

    int max() default 5;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
