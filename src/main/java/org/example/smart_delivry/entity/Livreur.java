package org.example.smart_delivry.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Livreur extends User{

    private String vehicule;
    private String teliphone;

}
