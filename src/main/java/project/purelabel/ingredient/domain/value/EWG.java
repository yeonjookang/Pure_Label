package project.purelabel.ingredient.domain.value;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class EWG {
    int grade;
}
