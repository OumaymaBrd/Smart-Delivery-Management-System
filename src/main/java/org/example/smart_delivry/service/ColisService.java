package org.example.smart_delivry.service;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.entity.Livreur;
import org.example.smart_delivry.enums.StatutColis;
import org.example.smart_delivry.dao.ColisDao;
import org.example.smart_delivry.dao.LivreurDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ColisService {

    private ColisDao colisDao;
    private LivreurDao livreurDao;

    public void setColisDao(ColisDao colisDao) {
        this.colisDao = colisDao;
    }

    public void setLivreurDao(LivreurDao livreurDao) {
        this.livreurDao = livreurDao;
    }

    public Colis enregistrerColis(Colis colis, Long livreurId) {
        Livreur livreur = livreurDao.findById(livreurId)
                .orElseThrow(() -> new IllegalArgumentException("Livreur non trouvé avec l'id: " + livreurId));

        colis.setLivreur(livreur);
        if (colis.getStatut() == null) {
            colis.setStatut(StatutColis.PREPARATION);
        }

        return colisDao.save(colis);
    }

    public Colis mettreAJourStatut(Long colisId, StatutColis nouveauStatut) {
        Colis colis = colisDao.findById(colisId)
                .orElseThrow(() -> new IllegalArgumentException("Colis non trouvé avec l'id: " + colisId));

        colis.setStatut(nouveauStatut);
        return colisDao.save(colis);
    }

    @Transactional(readOnly = true)
    public List<Colis> listerColisParLivreur(Long livreurId) {
        return colisDao.findByLivreurId(livreurId);
    }

    @Transactional(readOnly = true)
    public List<Colis> listerTousLesColis() {
        return colisDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Colis> trouverColisParId(Long id) {
        return colisDao.findById(id);
    }

    public Colis modifierColis(Long id, Colis colisModifie) {
        Colis colis = colisDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Colis non trouvé avec l'id: " + id));

        colis.setDestinataire(colisModifie.getDestinataire());
        colis.setAdresse(colisModifie.getAdresse());
        colis.setPoids(colisModifie.getPoids());

        return colisDao.save(colis);
    }

    public void supprimerColis(Long id) {
        if (!colisDao.existsById(id)) {
            throw new IllegalArgumentException("Colis non trouvé avec l'id: " + id);
        }
        colisDao.deleteById(id);
    }
}
