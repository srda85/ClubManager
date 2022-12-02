package com.example.clubmanager.repositories;

import com.example.clubmanager.models.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AbonnementRepository extends JpaRepository <Abonnement, Long>{

    List<Abonnement> findAbonnementByEleve_ID(long id);

    //ARRIVE ICI VENDREDI 23/09
//
////    @Query(value = "SELECT * ,SUM(montant) FROM abonnements")
//    @Query("SELECT * FROM abonnements")
//    List<Abonnement>selectAll;


}
