package com.example.clubmanager.models.forms;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AbonnementCreateForm {

    private LocalDate debutAbonnement;
    private int dureeAbonnement;
    int montant;
    private Long eleveId;
}
