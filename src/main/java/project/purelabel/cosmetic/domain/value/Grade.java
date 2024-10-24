package project.purelabel.cosmetic.domain.value;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Grade {
    좋음(2),
    보통(1),
    나쁨(0);

    private final int value;

    Grade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @JsonValue // JSON 직렬화 시 이 값이 사용됨
    public String toJson() {
        return this.name();
    }

    // Enum을 int 값으로부터 가져오는 메서드
    public static Grade fromValue(int value) {
        for (Grade grade : Grade.values()) {
            if (grade.value == value) {
                return grade;
            }
        }
        throw new IllegalArgumentException("Invalid grade value: " + value);
    }
}