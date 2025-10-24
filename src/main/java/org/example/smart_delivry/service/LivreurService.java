package org.example.smart_delivry.service;

import org.example.smart_delivry.entity.Livreur;
import org.example.smart_delivry.dao.LivreurDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class LivreurService {

    private final LivreurDao livreurDao;

    public LivreurService(LivreurDao livreurDao) {
        this.livreurDao = livreurDao;
    }

    public Livreur enregistrerLivreur(Livreur livreur) {
        if (livreurDao.findByTelephone(livreur.getTelephone()).isPresent()) {
            throw new IllegalArgumentException("Un livreur avec ce numéro de téléphone existe déjà");
        }
        return livreurDao.save(livreur);
    }

    @Transactional(readOnly = true)
    public List<Livreur> listerTousLesLivreurs() {
        return livreurDao.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Livreur> trouverLivreurParId(Long id) {
        return livreurDao.findByIdWithColis(id);
    }

    @Transactional(readOnly = true)
    public Optional<Livreur> trouverLivreurAvecColis(Long id) {
        return livreurDao.findByIdWithColis(id);
    }

    public Livreur modifierLivreur(Long id, Livreur livreurModifie) {
        Livreur livreur = livreurDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livreur non trouvé avec l'id: " + id));

        livreur.setNom(livreurModifie.getNom());
        livreur.setPrenom(livreurModifie.getPrenom());
        livreur.setVehicule(livreurModifie.getVehicule());
        livreur.setTelephone(livreurModifie.getTelephone());

        return livreurDao.save(livreur);
    }

    public void supprimerLivreur(Long id) {
        if (!livreurDao.existsById(id)) {
            throw new IllegalArgumentException("Livreur non trouvé avec l'id: " + id);
        }
        livreurDao.deleteById(id);
    }
}
