package project.purelabel.ingredient.domain;

import project.purelabel.ingredient.domain.entity.Ingredient;

import java.util.List;
import java.util.Set;

public interface IngredientRepository {
    List<Ingredient> findByPkIn(Set<Long> ids);
}
