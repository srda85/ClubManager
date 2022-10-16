package com.example.clubmanager.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stat_id;

    @Column
    private int annee;

    @Column
    private int month;

    //mettre à jour lorsqu'on créé une inscription
    @Column
    private Integer nbrInscriptions;

    @Column
    private Integer nbrElevesInscrits;

    @Column(name = "montant_total")
    private Integer montantsTotalMensuel;


//     @Column
//    private Integer nbrCeintureBlanche;
//
//    @Column
//    private Integer nbrCeintureBleue;
//
//    @Column
//    private Integer nbrCeintureViolette;


}
