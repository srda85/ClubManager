package com.example.clubmanager.models.forms;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EleveUpdateForm {

    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String adresse;
    private String gsm;
    private String email;
    private String ceinture;
    private List<Long> abonnementsId;
    private String role;
}
