package com.example.clubmanager.models.forms;

import lombok.Data;

@Data
public class StatisticCreateForm {

    private Long stat_id;

    private Integer annee;

    private String month;

    private Integer nbrInscriptions;

    private Integer nbrElevesInscrits;

    private Integer nbrCeintureBlanche;

    private Integer nbrCeintureBleue;

    private Integer nbrCeintureViolette;

    private Integer montantTotal;
}
