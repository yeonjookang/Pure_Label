package project.purelabel.cosmetic.application.response;

import lombok.Getter;
import project.purelabel.ingredient.domain.entity.Ingredient;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CosmeticAnalyzeResponseDto {
    private List<CosmeticAnalyzeIngredient> analyzeResult;

    public CosmeticAnalyzeResponseDto(List<Ingredient> ingredients) {
        this.analyzeResult = ingredients.stream()
                .map(ingredient -> new CosmeticAnalyzeIngredient(
                        ingredient.getPk(),
                        ingredient.getName(),
                        ingredient.getEwgGrade()
                ))
                .collect(Collectors.toList());
    }
}

