package project.purelabel.cosmetic.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import project.purelabel.cosmetic.domain.entity.Cosmetic;
import project.purelabel.cosmetic.domain.CosmeticRepository;

public interface CosmeticJpaRepository extends JpaRepository<Cosmetic,Long>, CosmeticRepository {

}
