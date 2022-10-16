package com.example.clubmanager.repositories;

import com.example.clubmanager.models.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EleveRepository extends JpaRepository<Eleve,Long> {

    //exemple de méthode pour récupèrer les éléments avec ce nom
    //Trois élément: findBy - Nom - Contains
    //Faut laisser l'ide completer le nom pour nous.
    //A voir si avec plusieurs tables il faut ajouter le nom de la table.
    List<Eleve>findByNomContains(String nom);

//    List<Eleve>findByNomAndPrenom(String nom, String prenom);
//    List<Eleve>findByNomOrPrenom(String nom, String prenom);
    List<Eleve>findByNomNotLike(String nom);

    List<Eleve>findByCeintureContains(String ceinture);
}
