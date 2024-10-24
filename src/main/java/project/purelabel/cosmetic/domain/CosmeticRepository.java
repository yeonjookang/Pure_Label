package project.purelabel.cosmetic.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.purelabel.cosmetic.domain.entity.Cosmetic;

import java.util.Optional;

public interface CosmeticRepository {
    Page<Cosmetic> findAll(Pageable pageable);
    @Query("SELECT c FROM Cosmetic c WHERE :search IS NULL OR c.name LIKE %:search%")
    Page<Cosmetic> findAllWithSearch(@Param("search") String search, Pageable pageable);
    Optional<Cosmetic> findById(Long cosmeticId);
}
