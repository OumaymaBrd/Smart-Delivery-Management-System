package org.example.smart_delivry.repository;

import org.example.smart_delivry.entity.Colis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColisRepository extends JpaRepository<Colis, Long> {

    List<Colis> findByLivreurId(Long livreurId);
}
