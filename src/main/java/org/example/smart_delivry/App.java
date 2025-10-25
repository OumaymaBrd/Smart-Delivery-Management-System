package org.example.smart_delivry;

import org.example.smart_delivry.entity.Colis;
import org.example.smart_delivry.entity.Livreur;
import org.example.smart_delivry.enums.StatutColis;
import org.example.smart_delivry.service.ColisService;
import org.example.smart_delivry.service.LivreurService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class App {

    public static void main(String[] args) {
        // Charger le contexte Spring depuis le fichier XML
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("=== Application de Gestion de Livraisons SmartLogi ===\n");

        // Récupérer les services depuis le conteneur Spring
        LivreurService livreurService = context.getBean("livreurService", LivreurService.class);
        ColisService colisService = context.getBean("colisService", ColisService.class);

        try {
            // 1. Créer et enregistrer des livreurs
            System.out.println("1. Création de livreurs...");
            Livreur livreur1 = new Livreur();
            livreur1.setNom("Alami");
            livreur1.setPrenom("Mohammed");
            livreur1.setVehicule("Moto");
            livreur1.setTelephone("0612345678");

            Livreur livreur2 = new Livreur();
            livreur2.setNom("Bennani");
            livreur2.setPrenom("Fatima");
            livreur2.setVehicule("Voiture");
            livreur2.setTelephone("0698765432");

            livreur1 = livreurService.enregistrerLivreur(livreur1);
            livreur2 = livreurService.enregistrerLivreur(livreur2);
            System.out.println("✓ Livreurs créés avec succès\n");

            // 2. Lister tous les livreurs
            System.out.println("2. Liste des livreurs:");
            List<Livreur> livreurs = livreurService.listerTousLesLivreurs();
            livreurs.forEach(l -> System.out.println("   - " + l.getPrenom() + " " + l.getNom() +
                    " (" + l.getVehicule() + ") - " + l.getTelephone()));
            System.out.println();

            // 3. Créer et enregistrer des colis
            System.out.println("3. Création de colis...");
            Colis colis1 = new Colis();
            colis1.setDestinataire("Ahmed Tazi");
            colis1.setAdresse("123 Rue Mohammed V, Casablanca");
            colis1.setPoids(2.5);
            colis1.setStatut(StatutColis.PREPARATION);

            Colis colis2 = new Colis();
            colis2.setDestinataire("Sara Idrissi");
            colis2.setAdresse("456 Avenue Hassan II, Rabat");
            colis2.setPoids(1.8);
            colis2.setStatut(StatutColis.PREPARATION);

            colis1 = colisService.enregistrerColis(colis1, livreur1.getId());
            colis2 = colisService.enregistrerColis(colis2, livreur2.getId());
            System.out.println("✓ Colis créés avec succès\n");

            // 4. Lister tous les colis
            System.out.println("4. Liste des colis:");
            List<Colis> colis = colisService.listerTousLesColis();
            colis.forEach(c -> System.out.println("   - Colis #" + c.getId() + " pour " +
                    c.getDestinataire() + " (" + c.getPoids() + "kg) - Statut: " + c.getStatut()));
            System.out.println();

            // 5. Mettre à jour le statut d'un colis
            System.out.println("5. Mise à jour du statut du colis #" + colis1.getId() + "...");
            colisService.mettreAJourStatut(colis1.getId(), StatutColis.EN_COURS);
            System.out.println("✓ Statut mis à jour: EN_COURS\n");

            // 6. Afficher les colis d'un livreur spécifique
            System.out.println("6. Colis assignés à " + livreur1.getPrenom() + " " + livreur1.getNom() + ":");
            List<Colis> colisLivreur1 = colisService.listerColisParLivreur(livreur1.getId());
            colisLivreur1.forEach(c -> System.out.println("   - Colis #" + c.getId() + " pour " +
                    c.getDestinataire() + " - Statut: " + c.getStatut()));
            System.out.println();

            // 7. Modifier un livreur
            System.out.println("7. Modification du véhicule du livreur #" + livreur1.getId() + "...");
            livreur1.setVehicule("Scooter");
            livreurService.modifierLivreur(livreur1.getId(), livreur1);
            System.out.println("✓ Véhicule mis à jour: Scooter\n");

            System.out.println("=== Tests terminés avec succès ===");

        } catch (Exception e) {
            System.err.println("Erreur: " + e.getMessage());
            e.printStackTrace();
        }

        // Fermer le contexte
        ((ClassPathXmlApplicationContext) context).close();
    }
}
