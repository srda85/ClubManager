package com.example.clubmanager.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Getter et setter obligatoire mais peuvent être implanté facilement avec @Data de lombock (je pense).
@Entity(name = "eleves")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Eleve {

    @Id
    //il semblerait que la strategie Identity fonctionne mieux pour la cascade
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    //L'annotation column n'est pas obligatoire tant que la variable a le même nom que la colonne de la bdd.
    @Column(name="nom")
    private String nom;
    private String prenom;

    @Column (name = "date_naissance")
    private LocalDate dateNaissance;

    private String adresse;

    private String gsm;

    private String email;

    private String ceinture;

    private String statut;

    private String role;

    private LocalDate dateCreationFiche;

    //Fetch LAZY signifie qu'il chargera les abonnements que sur demande avec getAbo...
    //mapped by prend la variable de l'autre coté et va creer une table intermédiaire.
    //Mapped by = listé par ..... Un élève à une collection (liste) d'abonnement
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "eleve", cascade = CascadeType.DETACH)
    private List<Abonnement> abonnements = new ArrayList<>();


    //Je dois quand même faire un constructeur car l'id est auto générée donc je ne dois pas l'ajouter
    public Eleve(String nom, String prenom, LocalDate dateNaissance, String adresse, String gsm, String email, String ceinture, String statut, String role, LocalDate dateCreationFiche) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.gsm = gsm;
        this.email = email;
        this.ceinture = ceinture;
        this.statut=statut;
        this.role=role;
        this.dateCreationFiche=dateCreationFiche;
    }
}
