package project.purelabel.cosmetic.domain.value;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
public class IngredientPk implements Serializable {
    private Long ingredientId;
}
