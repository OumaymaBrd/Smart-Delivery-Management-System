package org.example.smart_delivry.controller;

import org.example.smart_delivry.entity.Livreur;
import org.example.smart_delivry.service.LivreurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/livreurs")
public class LivreurController {

    private final LivreurService livreurService;

    @Autowired
    public LivreurController(LivreurService livreurService) {
        this.livreurService = livreurService;
    }

    /**
     * GET /livreurs - Récupérer tous les livreurs
     */
    @GetMapping
    public ResponseEntity<List<Livreur>> getAllLivreurs() {
        List<Livreur> livreurs = livreurService.listerTousLesLivreurs();
        return ResponseEntity.ok(livreurs);
    }

    /**
     * GET /livreurs/{id} - Récupérer un livreur par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Livreur> getLivreurById(@PathVariable Long id) {
        Optional<Livreur> livreur = livreurService.trouverLivreurParId(id);
        return livreur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /livreurs/{id}/colis - Récupérer un livreur avec ses colis
     */
    @GetMapping("/{id}/colis")
    public ResponseEntity<Livreur> getLivreurWithColis(@PathVariable Long id) {
        Optional<Livreur> livreur = livreurService.trouverLivreurAvecColis(id);
        return livreur.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /livreurs - Créer un nouveau livreur
     */
    @PostMapping
    public ResponseEntity<Livreur> createLivreur(@RequestBody Livreur livreur) {
        try {
            Livreur nouveauLivreur = livreurService.enregistrerLivreur(livreur);
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauLivreur);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /livreurs/{id} - Modifier un livreur existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<Livreur> updateLivreur(@PathVariable Long id, @RequestBody Livreur livreur) {
        try {
            Livreur livreurModifie = livreurService.modifierLivreur(id, livreur);
            return ResponseEntity.ok(livreurModifie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /livreurs/{id} - Supprimer un livreur
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivreur(@PathVariable Long id) {
        try {
            livreurService.supprimerLivreur(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
