package com.example.clubmanager.controllers;

import com.example.clubmanager.models.dto.StatisticDTO;
import com.example.clubmanager.service.stat.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.WebSocket;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ClubManager/statistiques")
@Secured({"ROLE_ADMIN"})
public class StatisticController {

    @Autowired
    StatService statService;

    @GetMapping("all")
    public List<StatisticDTO>getAll(){
        return statService.getAll();
    }


}
