package com.example.clubmanager.service.stat;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.Statistic;
import com.example.clubmanager.models.forms.AbonnementCreateForm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class StatisticScheduler {

    //Pas aussi bien que l'injection via constructeur
    @Autowired
    StatServiceMethods statServiceMethods;


    //Pas d'algorithmique car ici le but c'est la prévision.
    //Alogo se lance une fois par mois et crée une entrée
    //cron : minuit 15 tous les premiers du mois
    @Scheduled(cron = "0 15 0 1 * *")
    private void createMonthly(){
        Statistic statistic = new Statistic();
        LocalDate now = LocalDate.now();
       if (!statServiceMethods.statMoisAnneeExiste(now.getYear(),now.getMonth().getValue())){
           statistic.setMonth(now.getMonth().getValue());
           statistic.setAnnee(now.getYear());
           statistic.setMontantsTotalMensuel(statServiceMethods.getMontantTotalMensuel(now));
           statistic.setNbrInscriptions(statServiceMethods.getNbreInscriptionParMois(now));
           statistic.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
           statServiceMethods.create(statistic);
       }
    }

    public void setStatWhenCreateAbo(AbonnementCreateForm form){
        if (statServiceMethods.statMoisAnneeExiste(form.getDebutAbonnement().getYear(),form.getDebutAbonnement().getMonthValue())){
            //je récupère la statisique qui correspond au mois et à l'année de la création de l'abonnement
            Statistic statToUpdate=statServiceMethods.findStatToUpdate(form.getDebutAbonnement());
            statToUpdate.setNbrInscriptions(statToUpdate.getNbrInscriptions()+1);
            statToUpdate.setMontantsTotalMensuel(statServiceMethods.getMontantTotalMensuel(form.getDebutAbonnement()));
            statServiceMethods.statisticRepository.saveAndFlush(statToUpdate);
        }
        else {
            Statistic statistic = new Statistic();
            statistic.setMonth(form.getDebutAbonnement().getMonth().getValue());
                    statistic.setAnnee(form.getDebutAbonnement().getYear());
                    statistic.setMontantsTotalMensuel(statServiceMethods.getMontantTotalMensuel(form.getDebutAbonnement()));
                    statistic.setNbrInscriptions(statServiceMethods.getNbreInscriptionParMois(form.getDebutAbonnement()));
                    statistic.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
                    statServiceMethods.create(statistic);
        }
    }

    public void setStatWhenCreateEleve(){
        Statistic statistic = new Statistic();
        LocalDate now = LocalDate.now();
        if (!statServiceMethods.statMoisAnneeExiste(now.getYear(),now.getMonth().getValue())) {
            statistic.setMonth(now.getMonth().getValue());
            statistic.setAnnee(now.getYear());
            statistic.setMontantsTotalMensuel(statServiceMethods.getMontantTotalMensuel(now));
            statistic.setNbrInscriptions(statServiceMethods.getNbreInscriptionParMois(now));
            statistic.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
            statServiceMethods.create(statistic);
        }
        else {
            Statistic statToUpdate = statServiceMethods.findStatToUpdate(LocalDate.now());
            statToUpdate.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
            statServiceMethods.create(statToUpdate);
        }
    }

    public void setStatWhenUpdateAbonnement(LocalDate dateStatDiminue, LocalDate dateStatAugmente, int montantADiminuer, int montantAAugmenter){
        //pr vérifier les montants qui entrent
//        System.out.println(montantADiminuer);
//        System.out.println(montantAAugmenter);

        //je récupère les deux stats
        Statistic statQuiAugmente=statServiceMethods.findStatToUpdate(dateStatAugmente);
        Statistic statisticQuiDiminue=statServiceMethods.findStatToUpdate(dateStatDiminue);
        if (dateStatDiminue.isEqual(dateStatAugmente)) {
            statisticQuiDiminue.setMontantsTotalMensuel(statisticQuiDiminue.getMontantsTotalMensuel()+montantAAugmenter-montantADiminuer);
            statServiceMethods.statisticRepository.saveAndFlush(statisticQuiDiminue);
        }else {
            //stat qui augmente
            if (!statServiceMethods.statMoisAnneeExiste(dateStatAugmente.getYear(),dateStatAugmente.getMonth().getValue())) {
                //je récupère le mois et l'année du formulaire
                statQuiAugmente.setMonth(dateStatAugmente.getMonth().getValue());
                statQuiAugmente.setAnnee(dateStatAugmente.getYear());
                //j'initialise le montant
                statQuiAugmente.setMontantsTotalMensuel(statServiceMethods.getMontantTotalMensuel(dateStatAugmente));
                statQuiAugmente.setNbrInscriptions(statServiceMethods.getNbreInscriptionParMois(dateStatAugmente));
                statQuiAugmente.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
                statServiceMethods.create(statQuiAugmente);

                statisticQuiDiminue.setNbrElevesInscrits(statServiceMethods.getNbreEleveInscrits());
                statisticQuiDiminue.setMontantsTotalMensuel(statisticQuiDiminue.getMontantsTotalMensuel()-montantADiminuer);
                statisticQuiDiminue.setNbrInscriptions(statisticQuiDiminue.getNbrInscriptions()-1);
                statServiceMethods.create(statisticQuiDiminue);
            }

            else {
                Statistic statToUpdate = statServiceMethods.findStatToUpdate(dateStatAugmente);
                statToUpdate.setNbrInscriptions(statServiceMethods.getNbreEleveInscrits());
                statToUpdate.setMontantsTotalMensuel(statToUpdate.getMontantsTotalMensuel()+montantAAugmenter);
                //vérif pour le montant
                statServiceMethods.create(statToUpdate);

                //Montant pas bon
                statisticQuiDiminue.setMontantsTotalMensuel(statisticQuiDiminue.getMontantsTotalMensuel()-montantADiminuer);
                statisticQuiDiminue.setNbrInscriptions(statisticQuiDiminue.getNbrInscriptions());
                statServiceMethods.create(statisticQuiDiminue);
            }
        }
    }


    public void setStatWhenDeleteAbonnement(Abonnement abonnementSupprime){
        LocalDate dateAbo=abonnementSupprime.getDebutAbonnement();

        Statistic statisticQUiDiminue=statServiceMethods.findStatToUpdate(dateAbo);
        statisticQUiDiminue.setMontantsTotalMensuel(statisticQUiDiminue.getMontantsTotalMensuel()-abonnementSupprime.getMontant());
        statisticQUiDiminue.setNbrInscriptions(statisticQUiDiminue.getNbrInscriptions()-1);
        statServiceMethods.create(statisticQUiDiminue);
    }

    public void setStatWhenDeleteEleve(Eleve eleveSupprime){
        //Attention ici que si la stat n'existe pas j'aurai un problème. Théoriquement cela ne devrait pas être possible
        Statistic statisticQuiDiminue=statServiceMethods.findStatToUpdate(LocalDate.now());
        statisticQuiDiminue.setNbrElevesInscrits(statisticQuiDiminue.getNbrElevesInscrits()-1);
        statServiceMethods.create(statisticQuiDiminue);

    }


}
