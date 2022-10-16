package com.example.clubmanager.models.forms;


import lombok.Data;

import java.time.LocalDate;

@Data
public class EleveCreateForm {

    private String nom;

    private String prenom;

    private LocalDate dateNaissance;

    private String adresse;

    private String gsm;

    private String email;

    private String ceinture;

    private String role;


}
