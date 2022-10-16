package com.example.clubmanager.models.dto;

import com.example.clubmanager.models.Eleve;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AbonnementDTO {

    private Long ID;
    private LocalDate debutAbonnement;
    private LocalDate finAbonnement;
    int montant;
    private Long eleveId;
    private String eleve;

}
