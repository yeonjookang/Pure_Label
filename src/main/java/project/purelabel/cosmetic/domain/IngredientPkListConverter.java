package project.purelabel.cosmetic.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class IngredientPkListConverter implements AttributeConverter<Set<Long>, String> {

    // ','로 구분된 문자열로 변환 (DB에 저장될 때 호출)
    @Override
    public String convertToDatabaseColumn(Set<Long> ingredientPks) {
        return ingredientPks != null ? ingredientPks.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) : null;
    }

    // 문자열을 다시 Set<Long>으로 변환 (DB에서 읽을 때 호출)
    @Override
    public Set<Long> convertToEntityAttribute(String dbData) {
        return dbData != null ? Arrays.stream(dbData.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet()) : null;
    }
}