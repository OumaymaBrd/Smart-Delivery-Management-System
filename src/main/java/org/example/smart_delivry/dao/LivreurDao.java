package org.example.smart_delivry.dao;

import org.example.smart_delivry.entity.Livreur;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public class LivreurDao {

    private EntityManager entityManager;


    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Livreur save(Livreur livreur) {
        if (livreur.getId() == null) {
            entityManager.persist(livreur);
            return livreur;
        } else {
            return entityManager.merge(livreur);
        }
    }

    public Optional<Livreur> findById(Long id) {
        Livreur livreur = entityManager.find(Livreur.class, id);
        return Optional.ofNullable(livreur);
    }

    public Optional<Livreur> findByIdWithColis(Long id) {
        TypedQuery<Livreur> query = entityManager.createQuery(
                "SELECT l FROM Livreur l LEFT JOIN FETCH l.colis WHERE l.id = :id", Livreur.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Livreur> findAll() {
        TypedQuery<Livreur> query = entityManager.createQuery(
                "SELECT l FROM Livreur l", Livreur.class);
        return query.getResultList();
    }

    public List<Livreur> findByNom(String nom) {
        TypedQuery<Livreur> query = entityManager.createQuery(
                "SELECT l FROM Livreur l WHERE l.nom = :nom", Livreur.class);
        query.setParameter("nom", nom);
        return query.getResultList();
    }

    public Optional<Livreur> findByTelephone(String telephone) {
        TypedQuery<Livreur> query = entityManager.createQuery(
                "SELECT l FROM Livreur l WHERE l.telephone = :telephone", Livreur.class);
        query.setParameter("telephone", telephone);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Livreur> findByZone(String zone) {
        TypedQuery<Livreur> query = entityManager.createQuery(
                "SELECT l FROM Livreur l WHERE l.zone = :zone", Livreur.class);
        query.setParameter("zone", zone);
        return query.getResultList();
    }

    @Transactional
    public void delete(Livreur livreur) {
        if (entityManager.contains(livreur)) {
            entityManager.remove(livreur);
        } else {
            entityManager.remove(entityManager.merge(livreur));
        }
    }

    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(this::delete);
    }

    public boolean existsById(Long id) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(l) FROM Livreur l WHERE l.id = :id", Long.class);
        query.setParameter("id", id);
        return query.getSingleResult() > 0;
    }

    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(l) FROM Livreur l", Long.class);
        return query.getSingleResult();
    }
}
