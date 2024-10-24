package project.purelabel.user.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class SkinWorryListConverter implements AttributeConverter<List<SkinWorry>, String> {
    /**
     * 저장 시: List<SkinWorry>는 AttributeConverter에 의해 콤마로 구분된 문자열로 변환되어 데이터베이스에 저장
     * 조회 시: 데이터베이스에 저장된 콤마 구분 문자열은 다시 List<SkinWorry>로 변환되어 User 엔티티의 필드로 매핑
     */

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<SkinWorry> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<SkinWorry> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(SkinWorry::valueOf)
                .collect(Collectors.toList());
    }
}