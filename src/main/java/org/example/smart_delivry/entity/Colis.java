package org.example.smart_delivry.entity;

import lombok.*;
import org.example.smart_delivry.enums.StatutColis;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "livreur")
public class Colis {

    private Long id;
    private String destinataire;
    private String adresse;
    private Double poids;
    private StatutColis statut;
    private Livreur livreur;
}
