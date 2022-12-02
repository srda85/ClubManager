package com.example.clubmanager.service.eleve;

import com.example.clubmanager.mapper.EleveMapper;
import com.example.clubmanager.models.Eleve;
import com.example.clubmanager.models.dto.EleveDTO;
import com.example.clubmanager.models.dto.EleveReducedDataDto;
import com.example.clubmanager.models.forms.EleveCreateForm;
import com.example.clubmanager.models.forms.EleveUpdateForm;
import com.example.clubmanager.repositories.EleveRepository;
import com.example.clubmanager.service.abonnement.AboServiceImp;
import com.example.clubmanager.service.stat.StatisticScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EleveServiceImpl implements EleveService{

    @Autowired
    private AboServiceImp abonnementService;
    @Autowired
    StatisticScheduler statisticScheduler;

    @Autowired
    EleveServiceMethods eleveServiceMethods;
    private final EleveRepository eleveRepository;
    private final EleveMapper eleveMapper;


    public EleveServiceImpl(EleveRepository eleveRepository, EleveMapper eleveMapper) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
    }

    //J'utilise le repository pour récupèrer toutes les entrées. Grace au stream je les convertis en DTO
    @Override
    public List<EleveDTO> getAll() {
        return eleveRepository.
                findAll()
                .stream()
                .map(eleveMapper::toDTO)
                .toList();
    }

    public List<EleveReducedDataDto> getAllforTab() {
        return eleveRepository.
                findAll()
                .stream()
                //changé cette ligne
                .map(eleveMapper::toDTOforTab)
                .toList();
    }

    @Override
    public EleveDTO getOne(Long id) {
        return eleveRepository.findById(id)
                .map(eleveMapper::toDTO)
                .orElseThrow(()->new RuntimeException("id non existant"));
    }

    @Override
    public EleveDTO create(EleveCreateForm form) {
        if( form == null)
            throw new IllegalArgumentException("eleve inséré peut pas etre null");
        //retourner le dto n'est pas obligatoire on pourrait le faire avec un void
        Eleve eleve = eleveMapper.toEntity(form);
        eleve.setDateCreationFiche(LocalDate.now());
        EleveDTO eleveDTO = eleveMapper.toDTO(eleve);
        eleveRepository.saveAndFlush(eleve);

        eleveServiceMethods.miseAjourStatutEleve(eleve.getID());
        statisticScheduler.setStatWhenCreateEleve();

        return eleveDTO;
    }

    @Override
    public EleveDTO update(Long id, EleveUpdateForm toUpdate) {
        if (toUpdate == null || id == null)throw new IllegalArgumentException("params peut pas être null");
        if (!eleveRepository.existsById(id)) throw new RuntimeException("l'élément"+" "+id+" n'existe pas");

        //Je récupère le formulaire d'update et je le transforme en objet grace au mapper
        Eleve eleve = eleveMapper.toEntity(toUpdate);
        //Je rend à l'objet l'id utilisé
        eleve.setID(id);
        eleveRepository.saveAndFlush(eleve);

        eleveServiceMethods.miseAjourStatutEleve(eleve.getID());

        return eleveMapper.toDTO(eleve);
    }


    @Override
    public EleveDTO delete(Long id) {
        abonnementService.updateForDeleteEleve(id);
        //1. je récupère dans la bdd l'élève que je veux supprimer
        //2. je le supprime grace à la méthode du repository
        Eleve eleve = eleveRepository.findById(id).orElseThrow(() -> new RuntimeException("eleve non existant"));
        EleveDTO eleveDTO=eleveMapper.toDTO(eleve);

        statisticScheduler.setStatWhenDeleteEleve(eleve);

        eleveRepository.delete(eleve);
        return eleveDTO;
    }





}
