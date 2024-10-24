package project.purelabel.cosmetic.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import project.purelabel.cosmetic.domain.value.CosmeticType;
import project.purelabel.user.domain.value.SkinWorry;;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class CosmeticTypeListConverter implements AttributeConverter<List<CosmeticType>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<CosmeticType> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(CosmeticType::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<CosmeticType> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(CosmeticType::valueOf)
                .collect(Collectors.toList());
    }
}
