//package com.example.clubmanager.service;
//
//import com.example.clubmanager.models.Abonnement;
//import com.example.clubmanager.models.Eleve;
//import com.example.clubmanager.repositories.EleveRepository;
//import com.example.clubmanager.service.abonnement.AboServiceImp;
//import com.example.clubmanager.service.eleve.EleveServiceImpl;
//import com.example.clubmanager.service.eleve.EleveServiceMethods;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Component
//public class ScheduledService {
//
//    @Autowired
//    EleveServiceMethods eleveServiceMethods;
//
//    //Cron 2 heure du matin tous les jours(0 0 2 * * *)
//    // tt les 30 sec (cron = "*/30 * * * * *")
//    @Scheduled(cron = "0 0 2 * * *")
//    private void executeDayliCheck(){
//        eleveServiceMethods.checkEleveStatusDaily();
//    }
//
//
//
//
//
//}

