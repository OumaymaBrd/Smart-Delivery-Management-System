package org.example.smart_delivry.enums;

public enum StatutColis {
    PREPARATION("En préparation"),
    EN_COURS("En cours"),
    LIVRE("Livré");

    private final String libelle;

    StatutColis(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
