package com.example.clubmanager.models.forms;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AbonnementUpdateForm {

    private LocalDate debutAbonnement;
    private int dureeAbonnement;
    private int montant;
    private Long eleveId;
}
