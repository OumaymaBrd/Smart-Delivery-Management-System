package org.example.smart_delivry.dao;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.entity.Livreur;
import org.example.smart_delivry.enums.StatutColis;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public class ColisDao {

    private EntityManager entityManager;


    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Colis save(Colis colis) {
        if (colis.getId() == null) {
            entityManager.persist(colis);
            return colis;
        } else {
            return entityManager.merge(colis);
        }
    }

    public Optional<Colis> findById(Long id) {
        Colis colis = entityManager.find(Colis.class, id);
        return Optional.ofNullable(colis);
    }

    public List<Colis> findAll() {
        TypedQuery<Colis> query = entityManager.createQuery(
                "SELECT c FROM Colis c", Colis.class);
        return query.getResultList();
    }

    public List<Colis> findByStatut(StatutColis statut) {
        TypedQuery<Colis> query = entityManager.createQuery(
                "SELECT c FROM Colis c WHERE c.statut = :statut", Colis.class);
        query.setParameter("statut", statut);
        return query.getResultList();
    }

    public List<Colis> findByLivreur(Livreur livreur) {
        TypedQuery<Colis> query = entityManager.createQuery(
                "SELECT c FROM Colis c WHERE c.livreur = :livreur", Colis.class);
        query.setParameter("livreur", livreur);
        return query.getResultList();
    }

    public List<Colis> findByLivreurId(Long livreurId) {
        TypedQuery<Colis> query = entityManager.createQuery(
                "SELECT c FROM Colis c WHERE c.livreur.id = :livreurId", Colis.class);
        query.setParameter("livreurId", livreurId);
        return query.getResultList();
    }

    public List<Colis> findByDestination(String destination) {
        TypedQuery<Colis> query = entityManager.createQuery(
                "SELECT c FROM Colis c WHERE c.destination = :destination", Colis.class);
        query.setParameter("destination", destination);
        return query.getResultList();
    }

    @Transactional
    public void delete(Colis colis) {
        if (entityManager.contains(colis)) {
            entityManager.remove(colis);
        } else {
            entityManager.remove(entityManager.merge(colis));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    public boolean existsById(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM Colis c WHERE c.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM Colis c", Long.class);
        return query.getSingleResult();
    }

    public long countByStatut(StatutColis statut) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(c) FROM Colis c WHERE c.statut = :statut", Long.class);
        query.setParameter("statut", statut);
        return query.getSingleResult();
    }
}
