package com.example.clubmanager.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
//Builder permet de créer un objet de cette classe en completant les paramètre de manière plus organisée grace à la méthode .build
@Builder
public class EleveDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String nomPrenom;
    private String prenomNom;
    private LocalDate dateNaissance;
    private String adresse;
    private String gsm;
    private String email;
    private String ceinture;
    private List<Long> abonnementsId;
    private String statut;
    private String role;




//        @Data
//        @Builder
//        public static class AbonnementDto{
//            private Long ID;
//            private LocalDate debutAbonnement;
//            private LocalDate finAbonnement;
//            int montant;
//
//            public static AbonnementDTO fromEntity(Abonnement entity){
//                if (entity==null)return null;
//                return AbonnementDTO.builder()
//                        .ID(entity.getId())
//                        .debutAbonnement(entity.getDebutAbonnement())
//                        .finAbonnement(entity.getFinAbonnement())
//                        .montant(entity.getMontant())
//                        .build();
//            }
//        }
   }
