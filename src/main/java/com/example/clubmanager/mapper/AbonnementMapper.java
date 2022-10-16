package com.example.clubmanager.mapper;


import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.dto.AbonnementDTO;
import com.example.clubmanager.models.dto.AbonnementUpdateDTO;
import com.example.clubmanager.models.forms.AbonnementCreateForm;
import com.example.clubmanager.models.forms.AbonnementUpdateForm;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Component
public class AbonnementMapper {

    public AbonnementDTO toDTO (Abonnement entity){

        if (entity == null)return null;

        return AbonnementDTO.builder()
                .ID(entity.getId())
                .debutAbonnement(entity.getDebutAbonnement())
                .finAbonnement(entity.getFinAbonnement())
                .montant(entity.getMontant())
                .eleve(entity.getEleve().getNom()+" "+entity.getEleve().getPrenom())
                .eleveId(entity.getEleve().getID())
                .build();
    }

    public AbonnementUpdateDTO toUpdtaeDTO (Abonnement entity){
        if (entity==null)return null;

        //Petit algorithme pour calculer le nombre de mois entre deux pérdiodes
        Period period = Period.between(entity.getDebutAbonnement(),entity.getFinAbonnement());
        int months;
        if (period.getYears()>=1)months=(period.getYears()*12)+ period.getMonths();
        else months= period.getMonths();



        return AbonnementUpdateDTO.builder()
                .ID(entity.getId())
                .debutAbonnement(entity.getDebutAbonnement())
                .finAbonnement(entity.getFinAbonnement())
                .dureeAbonnement(months)
                .montant(entity.getMontant())
                .eleve(entity.getEleve().getNom()+" "+entity.getEleve().getPrenom())
                .eleveId(entity.getEleve().getID())
                .build();
    }

    //Méthode qui converti l'objet formulaire en objet eleve
    public Abonnement toEntity (AbonnementCreateForm form){
        if (form == null) return null;
        Abonnement abonnement = new Abonnement();

        abonnement.setDebutAbonnement(form.getDebutAbonnement().plusDays(1));

        //je crée la fin de l'abonnement en fonction du début et du nombre de mois
        //Je récupère la date de début d'abonnement que j'ai configurée sur plus 1 et j'ajoute la durée
        abonnement.setFinAbonnement(
                abonnement.getDebutAbonnement().plusMonths(form.getDureeAbonnement()));

        abonnement.setMontant(form.getMontant());
        return abonnement;
    }

    public Abonnement toEntity (AbonnementUpdateForm form){
        if (form == null) return null;
        Abonnement abonnement = new Abonnement();

        //PROBLEME DE DATE TJR PRESENT ajout if et jour + 1
        abonnement.setDebutAbonnement(form.getDebutAbonnement().plusDays(1));
        abonnement.setFinAbonnement(abonnement.getDebutAbonnement().plusMonths(form.getDureeAbonnement()));
        abonnement.setMontant(form.getMontant());
        return abonnement;
    }




//
//    public Eleve toEntity (EleveUpdateForm updateForm){
//        if (updateForm == null) return null;
//
//        Eleve eleve = new Eleve();
//        eleve.setNom(updateForm.getNom());
//        eleve.setPrenom(updateForm.getPrenom());
//        return eleve;
//    }
}
