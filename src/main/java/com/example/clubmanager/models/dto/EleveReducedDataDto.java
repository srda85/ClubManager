package com.example.clubmanager.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class EleveReducedDataDto {

    private Long id;
    private String nom;
    private String prenom;
    private String nomPrenom;
    private String prenomNom;
    private String ceinture;
    private String statut;

}
