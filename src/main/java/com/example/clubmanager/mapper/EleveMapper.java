package com.example.clubmanager.mapper;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.dto.EleveDTO;
import com.example.clubmanager.models.dto.EleveReducedDataDto;
import com.example.clubmanager.models.forms.EleveCreateForm;
import com.example.clubmanager.models.forms.EleveUpdateForm;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.service.abonnement.AboServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EleveMapper {

    @Autowired
    private AboServiceImp aboServiceImp;

    @Autowired
    private AbonnementRepository abonnementRepository;

    public EleveDTO toDTO (Eleve entity){

        if (entity == null)return null;

        return EleveDTO.builder()
                .id(entity.getID())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .nomPrenom(entity.getNom()+" "+entity.getPrenom())
                .prenomNom(entity.getPrenom()+" "+entity.getNom())
                .dateNaissance(entity.getDateNaissance())
                .adresse(entity.getAdresse())
                .email(entity.getEmail())
                .gsm(entity.getGsm())
                .ceinture(entity.getCeinture())
                //Juste pour récupèrer l'id des abonnements qui leurs sont associés.
                .abonnementsId(entity.getAbonnements().stream().filter(item -> item.getEleve()==entity ).map(Abonnement::getId).toList())
                .statut(entity.getStatut())
                .role(entity.getRole())
                .build();
    }

    public EleveReducedDataDto toDTOforTab (Eleve entity){
        if (entity == null)return null;

        return EleveReducedDataDto.builder()
                .id(entity.getID())
                .nom(entity.getNom())
                .prenom(entity.getPrenom())
                .nomPrenom(entity.getNom()+" "+entity.getPrenom())
                .prenomNom(entity.getPrenom()+" "+entity.getNom())
                .ceinture(entity.getCeinture())
                .statut(entity.getStatut())
                .build();
    }

    //Méthode qui converti l'objet formulaire en objet eleve
    public Eleve toEntity (EleveCreateForm form){
        if (form == null) return null;

        Eleve eleve = new Eleve();
        eleve.setNom(form.getNom());
        eleve.setPrenom(form.getPrenom());
        eleve.setDateNaissance(form.getDateNaissance().plusDays(1));
        eleve.setAdresse(form.getAdresse());
        eleve.setEmail(form.getEmail());
        eleve.setGsm(form.getGsm());
        eleve.setCeinture(form.getCeinture());
        eleve.setRole(form.getRole());
        eleve.setDateCreationFiche(LocalDate.now());
        return eleve;
    }

    public Eleve toEntity (EleveUpdateForm updateForm){
        if (updateForm == null) return null;

        Eleve eleve = new Eleve();
        eleve.setNom(updateForm.getNom());
        eleve.setPrenom(updateForm.getPrenom());
        eleve.setDateNaissance(updateForm.getDateNaissance().plusDays(1));
        eleve.setAdresse(updateForm.getAdresse());
        eleve.setEmail(updateForm.getEmail());
        eleve.setGsm(updateForm.getGsm());
        eleve.setCeinture(updateForm.getCeinture());
        eleve.setRole(updateForm.getRole());

        return eleve;
    }


}
