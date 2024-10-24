package project.purelabel.ingredient.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.purelabel.ingredient.domain.IngredientRepository;
import project.purelabel.ingredient.domain.entity.Ingredient;

public interface IngredientJpaRepository extends JpaRepository<Ingredient, Long>, IngredientRepository {
}
