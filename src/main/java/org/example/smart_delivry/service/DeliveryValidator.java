package org.example.smart_delivry.service;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.entity.Livreur;

public class DeliveryValidator {

    public String getMessage() {
        return "Service de validation de livraison SmartLogi - Système opérationnel";
    }

    public boolean validerLivreur(Livreur livreur) {
        if (livreur == null) {
            return false;
        }

        return livreur.getNom() != null && !livreur.getNom().trim().isEmpty()
                && livreur.getPrenom() != null && !livreur.getPrenom().trim().isEmpty()
                && livreur.getTelephone() != null && livreur.getTelephone().matches("\\d{10}")
                && livreur.getVehicule() != null && !livreur.getVehicule().trim().isEmpty();
    }

    public boolean validerColis(Colis colis) {
        if (colis == null) {
            return false;
        }

        return colis.getDestinataire() != null && !colis.getDestinataire().trim().isEmpty()
                && colis.getAdresse() != null && !colis.getAdresse().trim().isEmpty()
                && colis.getPoids() != null && colis.getPoids() > 0;
    }
}
