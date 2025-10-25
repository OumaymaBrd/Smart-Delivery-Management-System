package org.example.smart_delivry.controller;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.enums.StatutColis;
import org.example.smart_delivry.service.ColisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/colis")
public class ColisController {

    private final ColisService colisService;

    @Autowired
    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    /**
     * GET /colis - Récupérer tous les colis
     */
    @GetMapping
    public ResponseEntity<List<Colis>> getAllColis() {
        List<Colis> colis = colisService.listerTousLesColis();
        return ResponseEntity.ok(colis);
    }

    /**
     * GET /colis/{id} - Récupérer un colis par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Colis> getColisById(@PathVariable Long id) {
        Optional<Colis> colis = colisService.trouverColisParId(id);
        return colis.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /colis/livreur/{livreurId} - Récupérer tous les colis d'un livreur
     */
    @GetMapping("/livreur/{livreurId}")
    public ResponseEntity<List<Colis>> getColisByLivreur(@PathVariable Long livreurId) {
        List<Colis> colis = colisService.listerColisParLivreur(livreurId);
        return ResponseEntity.ok(colis);
    }

    /**
     * POST /colis - Créer un nouveau colis
     * Body: { "destinataire": "...", "adresse": "...", "poids": 2.5, "livreurId": 1 }
     */
    @PostMapping
    public ResponseEntity<Colis> createColis(@RequestBody ColisRequest request) {
        try {
            Colis colis = new Colis();
            colis.setDestinataire(request.getDestinataire());
            colis.setAdresse(request.getAdresse());
            colis.setPoids(request.getPoids());

            Colis nouveauColis = colisService.enregistrerColis(colis, request.getLivreurId());
            return ResponseEntity.status(HttpStatus.CREATED).body(nouveauColis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /colis/{id} - Modifier un colis existant
     */
    @PutMapping("/{id}")
    public ResponseEntity<Colis> updateColis(@PathVariable Long id, @RequestBody Colis colis) {
        try {
            Colis colisModifie = colisService.modifierColis(id, colis);
            return ResponseEntity.ok(colisModifie);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * PATCH /colis/{id}/statut - Mettre à jour le statut d'un colis
     * Query param: statut=EN_COURS
     */
    @PatchMapping("/{id}/statut")
    public ResponseEntity<Colis> updateStatut(@PathVariable Long id, @RequestParam StatutColis statut) {
        try {
            Colis colis = colisService.mettreAJourStatut(id, statut);
            return ResponseEntity.ok(colis);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /colis/{id} - Supprimer un colis
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColis(@PathVariable Long id) {
        try {
            colisService.supprimerColis(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Classes internes pour les requêtes
    public static class ColisRequest {
        private String destinataire;
        private String adresse;
        private Double poids;
        private Long livreurId;

        public String getDestinataire() { return destinataire; }
        public void setDestinataire(String destinataire) { this.destinataire = destinataire; }

        public String getAdresse() { return adresse; }
        public void setAdresse(String adresse) { this.adresse = adresse; }

        public Double getPoids() { return poids; }
        public void setPoids(Double poids) { this.poids = poids; }

        public Long getLivreurId() { return livreurId; }
        public void setLivreurId(Long livreurId) { this.livreurId = livreurId; }
    }

    public static class StatutRequest {
        private StatutColis statut;

        public StatutColis getStatut() { return statut; }
        public void setStatut(StatutColis statut) { this.statut = statut; }
    }
}
