package project.purelabel.user.api.request.customannotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ListSizeValidator implements ConstraintValidator<ListSize, List<?>> {
    private int min;
    private int max;

    @Override
    public void initialize(ListSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // null일 경우 유효하지 않음
        }
        return value.size() >= min && value.size() <= max;
    }
}