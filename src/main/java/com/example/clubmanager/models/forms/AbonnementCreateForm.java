package com.example.clubmanager.models.forms;

import com.example.clubmanager.models.Eleve;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AbonnementCreateForm {

    private LocalDate debutAbonnement;
    private int dureeAbonnement;
    int montant;
    private Long eleveId;
}
