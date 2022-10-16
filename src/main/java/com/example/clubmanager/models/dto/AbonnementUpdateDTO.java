package com.example.clubmanager.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AbonnementUpdateDTO {

    private Long ID;
    private LocalDate debutAbonnement;
    private LocalDate finAbonnement;

    //juste ajouté cette variable par rapport DTO normal
    private int dureeAbonnement;

    int montant;
    private Long eleveId;
    private String eleve;
}
