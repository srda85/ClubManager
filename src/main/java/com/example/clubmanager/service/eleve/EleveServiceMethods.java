package com.example.clubmanager.service.eleve;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.repositories.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Elvis;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EleveServiceMethods {
    @Autowired
    EleveRepository eleveRepository;

    private final List<String>statutsEleve= Arrays.asList("Membre du personnel","Pas d'abonnement","En ordre","Pas en ordre");
    private final List<String>roleEleve=Arrays.asList("élève","coach");



    private boolean verifStatutSiCoach(Eleve eleve){
        //je vérifie si son role est coach, si pas, il return true.
        if (eleve.getRole().contains(roleEleve.get(0))){
            return false;
        }else return true;
    }

    private boolean verifSiAbonnementEnOrdre(List<Abonnement>abonnementsList){
        for (Abonnement abonnement:abonnementsList) {
            if (abonnement.getFinAbonnement().isBefore(LocalDate.now())){
                return false;
            }
        }return true;
    }

    public void miseAjourStatutEleve(Long id){
        Eleve eleveToUpdateStatus=eleveRepository.findById(id).orElseThrow(()->new RuntimeException("Cet élève n'existe pas"));
        List<Abonnement> abonnementsDeEleveList=eleveToUpdateStatus.getAbonnements();

        if (verifStatutSiCoach(eleveToUpdateStatus)){
            eleveToUpdateStatus.setStatut(statutsEleve.get(0));
        }
        else if (abonnementsDeEleveList.isEmpty()) {
            eleveToUpdateStatus.setStatut(statutsEleve.get(1));
        }
        else if (verifSiAbonnementEnOrdre(abonnementsDeEleveList)){
            eleveToUpdateStatus.setStatut(statutsEleve.get(2));
        }else
            eleveToUpdateStatus.setStatut(statutsEleve.get(3));

        eleveRepository.saveAndFlush(eleveToUpdateStatus);

    }


    public void checkEleveStatusDaily(){

        System.out.println("Statut checked");
        //A checker j'ai du mettre le fetche en EAGER alors que j'aimerais le laisser en LAZY pour économiser de la ressource
        List<Eleve> eleveList=eleveRepository.findAll();
        for (Eleve eleve:eleveList) {
            List<Abonnement>abonnementListEleve=eleve.getAbonnements();

            //Si pas d'abonnement
            if (eleve.getAbonnements().isEmpty() && eleve.getRole().contains("élève"))
                eleve.setStatut("Pas d'abonnement");
            else if (eleve.getRole().contains("coach")) {
                eleve.setStatut("STAFF MEMBER");
            }
            //Si abonnement
            else {
                for (Abonnement abonnement:abonnementListEleve) {
                    if (abonnement.getFinAbonnement().isAfter(LocalDate.now()))eleve.setStatut("En ordre");
                    else eleve.setStatut("Pas en ordre");
                }
            }
            eleveRepository.saveAndFlush(eleve);
        }
    }


}
