package com.example.clubmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "abonnements")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "debut")
    private LocalDate debutAbonnement;
    @Column(name= "fin")
    private LocalDate finAbonnement;
    private int montant;

    private boolean emailEnvoye;

    @ManyToOne()
    private Eleve eleve;

    public Abonnement(LocalDate debutAbonnement, LocalDate finAbonnement, int montant, Eleve eleve) {
        this.debutAbonnement = debutAbonnement;
        this.finAbonnement = finAbonnement;
        this.montant = montant;
        this.eleve = eleve;
    }
}
