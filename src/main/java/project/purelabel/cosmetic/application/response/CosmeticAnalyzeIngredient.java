package project.purelabel.cosmetic.application.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.purelabel.ingredient.domain.value.EWG;

@Getter
@AllArgsConstructor
public class CosmeticAnalyzeIngredient {
    Long pk;
    String name;
    EWG ewgGrade;
}
