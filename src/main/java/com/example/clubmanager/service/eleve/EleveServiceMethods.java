package com.example.clubmanager.service.eleve;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.repositories.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class EleveServiceMethods {
    @Autowired
    EleveRepository eleveRepository;
    @Autowired
    AbonnementRepository abonnementRepository;

    private final List<String>statutsEleve= Arrays.asList("Membre du personnel","Pas d'abonnement","En ordre","Pas en ordre","Absent");
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

    private boolean verifSiAboAPlusDunAn(List<Abonnement>abonnementsList){
        boolean result=false;
        for (Abonnement abonnement:abonnementsList) {
            //je vérifie si la fin de l'abonnement précède de 1 la date actuelle et je mets sur true, je le fais pour tous les abos pour pas que ça soit faussé.
            if(abonnement.getFinAbonnement().isBefore(LocalDate.now().minusYears(1L))){
                result=true;
            }
            else {
                result =false;
            }
        }
        return result;
    }

    public void miseAjourStatutEleve(Long id){
        Eleve eleveToUpdateStatus=eleveRepository.findById(id).orElseThrow(()->new RuntimeException("Cet élève n'existe pas"));
        List<Abonnement> abonnementsDeEleveList=abonnementRepository.findAbonnementByEleve_ID(eleveToUpdateStatus.getID());

        if (verifStatutSiCoach(eleveToUpdateStatus)){
            eleveToUpdateStatus.setStatut(statutsEleve.get(0));
        }
        else if (abonnementsDeEleveList.isEmpty()) {
            eleveToUpdateStatus.setStatut(statutsEleve.get(1));
        }
        else if (verifSiAboAPlusDunAn(abonnementsDeEleveList)){
            eleveToUpdateStatus.setStatut(statutsEleve.get(4));
        }
        else if (verifSiAbonnementEnOrdre(abonnementsDeEleveList)){
            eleveToUpdateStatus.setStatut(statutsEleve.get(2));
        }else
            eleveToUpdateStatus.setStatut(statutsEleve.get(3));

        eleveRepository.saveAndFlush(eleveToUpdateStatus);

    }


    //Cron 2 heure du matin tous les jours(0 0 2 * * *)
    // tt les 30 sec (cron = "*/30 * * * * *")
    @Scheduled(cron = "0 0 2 * * *")
    public void checkEleveStatusDaily(){
        System.out.println("Statut checked daily");

        List<Eleve>eleveList=eleveRepository.findAll();
        if (!eleveList.isEmpty()){
            for (Eleve eleve:eleveList) {
                miseAjourStatutEleve(eleve.getID());
            }
        }
    }


}
