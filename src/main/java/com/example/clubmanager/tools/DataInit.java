package com.example.clubmanager.tools;

import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.User;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.repositories.EleveRepository;
import com.example.clubmanager.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class DataInit implements InitializingBean {

    //region constructeur et variables
    private final EleveRepository eleveRepository;
    private final AbonnementRepository abonnementRepository;

    public DataInit(EleveRepository eleveRepository, AbonnementRepository abonnementRepository, UserRepository userRepository) {
        this.eleveRepository = eleveRepository;
        this.abonnementRepository = abonnementRepository;
        this.userRepository = userRepository;
    }
    //endregion

    //region Generation de données
    Eleve neutre = new Eleve("CORBEILLE","ENTITY",LocalDate.now(),"Pas d'adresse","111111","AUCUN","AUCUNE","Pas d'abonnement","AUCUN",LocalDate.now());
//    Eleve eleve1=new Eleve("Rda","Sébastien", LocalDate.of(1985,9,4),"chaussée du chateau 4","486364954","ruizdearcaute@gmail.com","noire","En ordre","coach",LocalDate.of(2020,9,1));
    Eleve eleve2= new Eleve("Romero","Marcelo", LocalDate.of(1980,6,12),"rue de la paix 10","5963216","marcelo@hot.com","blanche","Pas d'abonnement","élève",LocalDate.now());
    Eleve eleve3=new  Eleve("Ciu","Eleo", LocalDate.of(1990,8,14),"place victoire","4535465465465","ciu@hot.com","bleue","Pas d'abonnement","élève",LocalDate.now());
//    Eleve eleve4=new Eleve("Delahouse","Jean", LocalDate.of(1978,9,4),"rue victoire 26","2164216","jean@hot.com","violette","Pas en ordre","élève",LocalDate.now());
//    Eleve eleve5= new Eleve("Roi","Alfred", LocalDate.of(1968,6,12),"rue de la joie 10","5963216","aflred@hot.com","blanche","Pas d'abonnement","élève",LocalDate.now());

    private final List<Eleve> eleveList = List.of(neutre, eleve2,eleve3);

//    private final List<Abonnement> abonnementList= Arrays.asList(
//            new Abonnement(LocalDate.of(2020,9,4),LocalDate.of(2021,9,4),400,eleve1),
//            new Abonnement(LocalDate.of(2022,8,4),LocalDate.of(2023,8,4),400,eleve2),
//             new Abonnement(LocalDate.now(),LocalDate.now().plusMonths(6),400,eleve3),
//            new Abonnement(LocalDate.of(2022,9,5),LocalDate.of(2022,10,5),400,eleve4),
//            new Abonnement(LocalDate.of(2018,9,24),LocalDate.of(2019,9,24),400,eleve1)
//    );

    //endregion

    private final UserRepository userRepository;

    //Tester après avec nom différents
    private List<User> users = Arrays.asList(
//            new User("srda85", new BCryptPasswordEncoder().encode("password"), true, List.of("USER")),
            new User("srda85", new BCryptPasswordEncoder().encode("bruformjava22"), true, List.of("ADMIN")),
            new User("ADMIN", new BCryptPasswordEncoder().encode("password"), true, List.of("ADMIN"))
    );



    @Override
    public void afterPropertiesSet() throws Exception {
        eleveRepository.saveAllAndFlush(eleveList);
//        abonnementRepository.saveAllAndFlush(abonnementList);
        userRepository.saveAll(users);

    }


}
