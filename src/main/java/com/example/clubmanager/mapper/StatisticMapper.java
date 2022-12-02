package com.example.clubmanager.mapper;

import com.example.clubmanager.models.Statistic;
import com.example.clubmanager.models.dto.StatisticDTO;
import org.springframework.stereotype.Component;

import java.text.DateFormatSymbols;

@Component
public class StatisticMapper {

    public StatisticDTO toDTO (Statistic entity){

        if (entity == null)return null;

        return StatisticDTO.builder()
                .stat_id(entity.getStat_id())
                .annee(entity.getAnnee())
                .montantTotal(entity.getMontantsTotalMensuel())
                .month(new DateFormatSymbols().getMonths()[entity.getMonth()-1])
                .nbrElevesInscrits(entity.getNbrElevesInscrits())
                .nbrInscriptions(entity.getNbrInscriptions())
                .build();
    }

//    ARRIVE ICI
//    public Statistic toEntity (StatisticCreateForm statisticCreateForm){
//        if ()
//    }
}
