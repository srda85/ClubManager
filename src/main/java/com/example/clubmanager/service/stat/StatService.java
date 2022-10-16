package com.example.clubmanager.service.stat;

import com.example.clubmanager.mapper.StatisticMapper;
import com.example.clubmanager.models.Statistic;
import com.example.clubmanager.models.dto.StatisticDTO;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class StatService {


    @Autowired
    StatisticRepository statisticRepository;

    @Autowired
    StatisticMapper statisticMapper;

    public List<StatisticDTO>getAll (){
        return statisticRepository.findAll().stream().map(statisticMapper::toDTO).toList();
    }


}
