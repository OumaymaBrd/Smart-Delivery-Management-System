package org.example.smart_delivry.repository;

import org.example.smart_delivry.entity.Livreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LivreurRepository extends JpaRepository<Livreur, Long> {

    Optional<Livreur> findByTelephone(String telephone);

    @Query("SELECT l FROM Livreur l LEFT JOIN FETCH l.colis WHERE l.id = :id")
    Optional<Livreur> findByIdWithColis(Long id);
}
