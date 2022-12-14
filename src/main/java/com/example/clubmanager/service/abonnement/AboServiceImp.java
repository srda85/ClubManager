package com.example.clubmanager.service.abonnement;


import com.example.clubmanager.email.EmailSenderService;
import com.example.clubmanager.mapper.AbonnementMapper;
import com.example.clubmanager.models.Abonnement;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.dto.AbonnementDTO;
import com.example.clubmanager.models.dto.AbonnementUpdateDTO;
import com.example.clubmanager.models.forms.AbonnementCreateForm;
import com.example.clubmanager.models.forms.AbonnementUpdateForm;
import com.example.clubmanager.repositories.AbonnementRepository;
import com.example.clubmanager.repositories.EleveRepository;
import com.example.clubmanager.service.eleve.EleveServiceMethods;
import com.example.clubmanager.service.stat.StatisticScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@EnableScheduling
public class AboServiceImp implements AboService {

    //region constructeur

    @Autowired
    private EmailSenderService emailSenderServiceS;
    private final AbonnementRepository repository;
    private final AbonnementMapper mapper;
    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private EleveServiceMethods eleveServiceMethods;

    @Autowired
    private StatisticScheduler statisticScheduler;

    public AboServiceImp(AbonnementRepository repository, AbonnementMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    //endregion

    @Override
    public List<AbonnementDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<AbonnementUpdateDTO>getAllforUpdate(){
        return repository.findAll().stream().map(mapper::toUpdtaeDTO).toList();
    }

    public List<AbonnementDTO> getallByEleveId(long id){
        return repository.findAbonnementByEleve_ID(id)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public AbonnementDTO create(AbonnementCreateForm form) {
        if( form == null)
            throw new IllegalArgumentException("le formulaire ne peut pas ??tre null");
        //Je transforme le formulaire en objet abonnement
        Abonnement abonnement = mapper.toEntity(form);

        if (checkAllAboByEleveId(form))
            throw new RuntimeException("Cr??ation impossible : Certaine date sont d??j?? occup??e");

        //Jet Set l'??leve de l'objet grace au formulaire
        abonnement.setEleve(eleveRepository.findById(form.getEleveId())
                .orElseThrow(()->new RuntimeException("Pas de Correspondance pour EleveID")));

        //R??activer plus tard
//        emailSenderServiceS.send("sceuba@gmail.com",abonnement.getEleve().getEmail(),"Votre nouvel abonnement chez PhoenixADA",
//                "Bonjour "+abonnement.getEleve().getPrenom()+","+
//                "\n\n\n\n Vous avez souscrit ?? un nouvel abonnement chez PhoenixADA BJJ."+
//                        "\n\n\t Il d??bute le "+abonnement.getDebutAbonnement().format(DateTimeFormatter.ofPattern("dd-MM-yy"))+
//                        "\n\n\t et il prendra fin le "+abonnement.getFinAbonnement().format(DateTimeFormatter.ofPattern("dd-MM-yy"))+
//                        "\n\n\n\n Toute l'??quipe PhoenixADA te remercie."+
//                        "\t\n\n\n\n A bient??t sur le tatami."
//                );

        AbonnementDTO abonnementDTO=mapper.toDTO(repository.saveAndFlush(abonnement));

        //Je mets ?? jour le statut de l'??l??ve suite ?? la cr??ation d'un abonnement
        eleveServiceMethods.miseAjourStatutEleve(form.getEleveId());

        //Je mets ?? jour les statistiques
        statisticScheduler.setStatWhenCreateAbo(form);

        return abonnementDTO;
    }
    @Override
    public AbonnementDTO getOne(Long id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(()->new RuntimeException("Id introuvable")));
    }

    @Override
    public AbonnementDTO update(Long id, AbonnementUpdateForm toUpdate) {
        if (toUpdate == null || id == null)throw new IllegalArgumentException("params peut pas ??tre null");
        if (!repository.existsById(id)) throw new RuntimeException("l'??l??ment"+" "+id+" n'existe pas");
        Abonnement abonnementDorigine=repository.findById(id).orElseThrow(()-> new RuntimeException("Abonnement introuvable"));
        LocalDate dateAboPrecedent=abonnementDorigine.getDebutAbonnement();
        int montantAboPrec=abonnementDorigine.getMontant();

//        System.out.println("date origine:"+dateAboPrecedent);
//        System.out.println("date de fin : "+toUpdate.getDebutAbonnement());
//        System.out.println("Montant origine :"+abonnementDorigine.getMontant());
//        System.out.println("Montant update :"+toUpdate.getMontant());


        Abonnement abonnement = mapper.toEntity(toUpdate);
        abonnement.setId(id);
        //faut aussi set l'??leve car on n'a que son ID dans le formulaire
        abonnement.setEleve(eleveRepository.findById(toUpdate.getEleveId()).orElseThrow(()->new RuntimeException("Pas de Correspondance pour EleveID")));;

        AbonnementDTO abonnementDTO=mapper.toDTO(repository.saveAndFlush(abonnement));

        eleveServiceMethods.miseAjourStatutEleve(toUpdate.getEleveId());

        statisticScheduler.setStatWhenUpdateAbonnement(dateAboPrecedent, abonnement.getDebutAbonnement(),montantAboPrec,toUpdate.getMontant());
//        Adapter une m??thode stat pr qu'elle se mette ?? jour
//        statisticScheduler.setStatWhenCreateAbo(toUpdate);

        return abonnementDTO;
    }

    @Override
    public AbonnementDTO delete(Long id) {
        Abonnement abonnement = repository.findById(id).orElseThrow(() -> new RuntimeException("Abonnement non existant"));
        Long idEleve=abonnement.getEleve().getID();

        repository.delete(abonnement);

        eleveServiceMethods.miseAjourStatutEleve(idEleve);

        statisticScheduler.setStatWhenDeleteAbonnement(abonnement);

        return mapper.toDTO(abonnement);
    }

    public void updateForDeleteEleve(Long eleveId){
        //1. je r??cup??re tous  les abonnements qui correspondent ?? l'??l??ve ?? supprimer.
        List<Abonnement>abonnementList  = repository.findAbonnementByEleve_ID(eleveId);
        //Pour tous les abonnements de la liste, je SET l'??l??ve sur l'??l??ve CORBEILLE
        //Ensuite je sauvegarde chacun
        Eleve eleveCorbeille = eleveRepository.findById(1L).orElseThrow(()->new RuntimeException("Pas de Correspondance pour EleveID"));
        for (Abonnement abonnement:abonnementList) {
            abonnement.setEleve(eleveCorbeille);
        }
       repository.saveAllAndFlush(abonnementList);
    }

    private List<Abonnement> getAllEntityByEleveId(Long idEleve){
        return repository.findAbonnementByEleve_ID(idEleve);
    }

    private boolean checkAllAboByEleveId(AbonnementCreateForm form){
        List<Abonnement>abonnementList=getAllEntityByEleveId(form.getEleveId());
        LocalDate finFormAbo;
        if (!abonnementList.isEmpty()){
            for (Abonnement abonnement:abonnementList) {
                finFormAbo=form.getDebutAbonnement().plusMonths(form.getDureeAbonnement());
                //si d??but avant la fin et apr??s le d??but
                if (form.getDebutAbonnement().isBefore(abonnement.getFinAbonnement())&&form.getDebutAbonnement().isAfter(abonnement.getDebutAbonnement())){
                    return true;
                //si un abonnement se trouve dans la r??gion du nouvel abonnement (cad entre sa date de d??but et sa date de fin)
                } else if (form.getDebutAbonnement().isBefore(abonnement.getDebutAbonnement())&&finFormAbo.isAfter(abonnement.getFinAbonnement())) {
                    return true;

                //idem que le premier pour la fin
                } else if (finFormAbo.isBefore(abonnement.getFinAbonnement())&&finFormAbo.isAfter(abonnement.getDebutAbonnement())) {
                    return true;
                }
            }
        }
        return false;
    }


}
