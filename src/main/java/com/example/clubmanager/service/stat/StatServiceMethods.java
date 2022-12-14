package com.example.clubmanager.service.stat;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.Statistic;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.repositories.EleveRepository;
import com.example.clubmanager.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatServiceMethods {

    @Autowired
    AbonnementRepository abonnementRepository;
    @Autowired
    StatisticRepository statisticRepository;
    @Autowired
    EleveRepository eleveRepository;

    public Integer getElevesParCeinture(String ceinture){
        return eleveRepository.findByCeintureContains(ceinture).size();
    }

    public List<Eleve> getElevesInscrits(){
        return eleveRepository.findAll();
    }

    public Boolean statMoisAnneeExiste(int annee, int mois){
        List<Statistic>statisticList=statisticRepository.findAll();
        for (Statistic stat:statisticList) {
            if (stat.getAnnee()==annee && stat.getMonth()==mois) return true;
        }
        return false;
    }

    public void create (Statistic statistic){
        statisticRepository.saveAndFlush(statistic);
    }

    public Statistic findStatToUpdate (LocalDate date){
        List<Statistic>statisticList=statisticRepository.findAll();
        Statistic statToReturn = null;
        if (statisticList.isEmpty())statToReturn=new Statistic();
        for (Statistic stat:statisticList) {
            if(statMoisAnneeExiste(date.getYear(),date.getMonthValue())){
                statToReturn = statisticList.stream()
                        .filter(statistic -> statistic.getMonth()== date.getMonthValue()&&statistic.getAnnee()==date.getYear())
                        .toList().get(0);
            }else {
                statToReturn = new Statistic();
            }
        }
        return statToReturn;
        //Ajuster ici je pense que je peux enlever cette partie
//        List<Statistic>statTrie=statisticList.stream()
//                .filter(statistic -> statistic.getMonth()== date.getMonthValue()&&statistic.getAnnee()==date.getYear())
//                .toList();
//        if (statTrie.get(0)==null)return null;
//        else return statTrie.get(0);
    }

    public Integer getMontantTotalMensuel(LocalDate date){
        if (abonnementRepository.findAll().isEmpty())return 0;
        return abonnementRepository.findAll().stream()
                .filter(item -> item.getDebutAbonnement().getYear()==date.getYear()&&item.getDebutAbonnement().getMonth().equals((date.getMonth())))
                .map(Abonnement::getMontant)
                .reduce(0,Integer::sum);
    }

    public Integer getNbreInscriptionParMois(LocalDate date){
        int nbrAbonnement=0;
        //au cas o?? aucun abonnement
        if(abonnementRepository.findAll().isEmpty()){
            return 0;
        }
        //je les r??cup??re tous
        List<Abonnement>abonnementList=abonnementRepository.findAll();

        //je v??rifi?? si la date correspond ?? un abonnement qui existe d??j?? et si ce n'est pas le cas, je renvoie d'office 0 - Attention m??thode caduc

        for (Abonnement abonnement:abonnementList) {
            if (abonnement.getDebutAbonnement().getYear()==date.getYear()&&abonnement.getDebutAbonnement().getMonth().getValue()==date.getMonth().getValue()){
                nbrAbonnement=+1;
            }
        }
        return nbrAbonnement;

        //je renvoie le nombre d'abonnement qui correspondent ?? cette date - Methode caduc ?? cause du if

//        return abonnementRepository.findAll().stream()
//                .filter(item -> item.getDebutAbonnement().getYear()==date.getYear()&&item.getDebutAbonnement().getMonth().equals((date.getMonth())))
//                .toList().size();
    }

    public Integer getNbreEleveInscrits( ){
        //-1 car j'enl??ve l'??l??ve corbeille
        return eleveRepository.findAll().size()-1;
    }

    public Statistic miseAJourMontantMensuel(Statistic stat, int montantMoins, int montantPlus){
        stat.setMontantsTotalMensuel(stat.getMontantsTotalMensuel()+montantPlus-montantMoins);
        return stat;
    }

}
