package com.example.clubmanager.email;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.repositories.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class SchedulerMail {

    @Autowired
    AbonnementRepository abonnementRepository;

    @Autowired
    EmailSenderService emailSenderService;

    //Demander s'il ne vaut pas mieux le me
    // ttre dans une classe et s'il faut pas déplacer ailleurs le @EnableScheduling
    //MODIFIER LE TIMING
    //Cron (cron = "0 0 6 * * *")
//    (cron = "*/30 * * * * *")
    @Scheduled(cron="0 0 6 * * *")
    public void testScheduler () {
        System.out.println("MAIL FONCTION - 6 heure du matin");
        List<Abonnement> abonnementsList=abonnementRepository.findAll();
        List<Abonnement>abonnementsExpire=abonnementsList
                .stream()
                //je peux normalement enlever le premier &&, je l'ai mis pour la démo
                .filter(item -> item.getFinAbonnement().isAfter(LocalDate.now().minusDays(4)) && item.getFinAbonnement().isBefore(LocalDate.now().plusDays(10))&& !item.isEmailEnvoye())
                .toList();

        for (Abonnement item:abonnementsExpire) {
            System.out.println("Cet abonnement prendra fin BIENTOT "+item.getFinAbonnement()+" "+item.getEleve().getEmail());
            //CHANGER LE MAIL QUAND OFFICIEL
            emailSenderService.send(
                    "sceuba@gmail.com",
                    item.getEleve().getEmail(),
                    "PhoenixADA - Expiration abonnement",
                    "Bonjour "+item.getEleve().getPrenom()+","+"\n\n\n"+"Juste un petit message pour te dire que ton abonnement chez PHOENIXADA Bjj est sur le point d'expirer."
                            +"\n\n\n La date de fin est le "+item.getFinAbonnement().format(DateTimeFormatter.ofPattern("dd-MM-yy"))+"."
                    +"\n\n\n \t A bientôt sur le tatami"
                    +"\n\n\n\n\n\n\n\n L'équipe PHOENIXADA"
                    );
            item.setEmailEnvoye(true);
            abonnementRepository.saveAndFlush(item);
        }
    }

}
