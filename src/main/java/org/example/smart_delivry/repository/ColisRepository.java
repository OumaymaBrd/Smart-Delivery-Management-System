package org.example.smart_delivry.repository;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.enums.StatutColis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, Long> {

    List<Colis> findByLivreurId(Long livreurId);

    List<Colis> findByStatut(StatutColis statut);

    @Query("SELECT c FROM Colis c WHERE c.livreur.id = :livreurId AND c.statut = :statut")
    List<Colis> findByLivreurIdAndStatut(Long livreurId, StatutColis statut);
}
