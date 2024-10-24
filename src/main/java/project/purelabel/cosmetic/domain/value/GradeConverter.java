package project.purelabel.cosmetic.domain.value;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GradeConverter implements AttributeConverter<Grade, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Grade attribute) {
        return (attribute != null) ? attribute.getValue() : null;
    }

    @Override
    public Grade convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? Grade.fromValue(dbData) : null;
    }
}
