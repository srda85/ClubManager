package com.example.clubmanager.models.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
public class StatisticDTO {

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
