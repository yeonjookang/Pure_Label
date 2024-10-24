package project.purelabel.cosmetic.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.purelabel.cosmetic.domain.CosmeticTypeListConverter;
import project.purelabel.cosmetic.domain.IngredientPkListConverter;
import project.purelabel.cosmetic.domain.value.CosmeticType;
import project.purelabel.cosmetic.domain.value.Grade;
import project.purelabel.cosmetic.domain.value.GradeConverter;
import project.purelabel.user.domain.SkinWorryListConverter;
import project.purelabel.user.domain.value.SkinType;
import project.purelabel.user.domain.value.SkinWorry;

import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cosmetic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pk;
    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private String imageUrl;
    @NotNull
    private int price;
    @Convert(converter = CosmeticTypeListConverter.class)
    @NotNull
    private List<CosmeticType> cosmeticTypes;
    @Convert(converter = IngredientPkListConverter.class)
    @Column(name="ingredient_pks")
    @NotNull
    private Set<Long> ingredientsPks;
    @NotNull
    private double rating;
    @NotNull
    @Convert(converter = GradeConverter.class)
    private Grade grade;
    @NotNull
    @Enumerated(EnumType.STRING)
    private SkinType skinType;
    @Convert(converter = SkinWorryListConverter.class)
    @Column(name="skin_worries")
    @NotNull
    private List<SkinWorry> skinWorries;
}
