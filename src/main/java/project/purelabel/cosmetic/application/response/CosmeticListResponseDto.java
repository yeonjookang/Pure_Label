package project.purelabel.cosmetic.application.response;

import lombok.Builder;
import lombok.Getter;
import project.purelabel.cosmetic.domain.entity.Cosmetic;
import project.purelabel.cosmetic.domain.value.Grade;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;

@Getter
public class CosmeticListResponseDto {
    private Long pk;
    private String name;
    private String imageUrl;
    private Grade grade;
    private SkinType skinType;
    private List<SkinWorry> skinWorries;
    private double rating;

    @Builder
    public CosmeticListResponseDto(Cosmetic cosmetic) {
        this.pk = cosmetic.getPk();
        this.name = cosmetic.getName();
        this.imageUrl = cosmetic.getImageUrl();
        this.grade = cosmetic.getGrade();
        this.skinType = cosmetic.getSkinType();
        this.skinWorries = cosmetic.getSkinWorries();
        this.rating = cosmetic.getRating();
    }
}
