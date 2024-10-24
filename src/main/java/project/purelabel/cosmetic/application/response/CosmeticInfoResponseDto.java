package project.purelabel.cosmetic.application.response;

import lombok.Builder;
import lombok.Getter;
import project.purelabel.cosmetic.domain.entity.Cosmetic;
import project.purelabel.cosmetic.domain.value.CosmeticType;
import project.purelabel.cosmetic.domain.value.Grade;

import java.util.List;

@Getter
public class CosmeticInfoResponseDto {
    private String name;
    private String brand;
    private int price;
    private String imageUrl;
    private List<CosmeticType> cosmeticTypes;
    private Grade grade;
    private List<String> ingredients;
    private double rating;

    @Builder
    public CosmeticInfoResponseDto(Cosmetic cosmetic, List<String> ingredients) {
        this.name = cosmetic.getName();
        this.brand = cosmetic.getBrand();
        this.price = cosmetic.getPrice();
        this.imageUrl = cosmetic.getImageUrl();
        this.cosmeticTypes = cosmetic.getCosmeticTypes();
        this.grade = cosmetic.getGrade();
        this.rating = cosmetic.getRating();
        this.ingredients = ingredients;
    }
}
