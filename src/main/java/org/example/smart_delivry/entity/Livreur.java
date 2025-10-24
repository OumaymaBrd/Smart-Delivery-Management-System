package org.example.smart_delivry.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "colis")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Livreur {

    private Long id;
    private String nom;
    private String prenom;
    private String vehicule;
    private String telephone;

    @Builder.Default
    private List<Colis> colis = new ArrayList<>();
}
