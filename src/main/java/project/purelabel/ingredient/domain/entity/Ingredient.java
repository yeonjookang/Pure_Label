package project.purelabel.ingredient.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.purelabel.ingredient.domain.value.EWG;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Long pk;
    @NotNull
    private String name;
    @NotNull
    @Enumerated
    private EWG ewgGrade;
}
