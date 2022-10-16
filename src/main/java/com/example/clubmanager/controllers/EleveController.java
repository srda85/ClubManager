package com.example.clubmanager.controllers;

import com.example.clubmanager.models.dto.EleveDTO;
import com.example.clubmanager.models.dto.EleveReducedDataDto;
import com.example.clubmanager.models.forms.EleveCreateForm;
import com.example.clubmanager.models.forms.EleveUpdateForm;
import com.example.clubmanager.service.eleve.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Cross origin  permet de reègler le problème cors qu'on peut avoir avec Angular 13+ quand on fait une requête
@CrossOrigin
@RestController
@RequestMapping("/ClubManager/eleves")
@Secured("ROLE_ADMIN")
public class EleveController {

    @Autowired
    private EleveService eleveService;


    @GetMapping("/all")
    public List<EleveDTO> getAll(Authentication auth){
        System.out.println(auth.getName());
        return eleveService.getAll();
    }

    @GetMapping("allforTab")
    public List<EleveReducedDataDto> getAllforTab(){
        return eleveService.getAllforTab();
    }


    @GetMapping
    @RequestMapping("{id}")
    public  EleveDTO getById(@PathVariable Long id){
        return eleveService.getOne(id);
     }


    @PostMapping
    public EleveDTO create(@RequestBody final EleveCreateForm form){
        return eleveService.create(form);
    }


    @DeleteMapping(value = "{id}")
    //demander alex à quoi ça servait
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable Long id){
        eleveService.delete(id);
    }

// a développer
//    @GetMapping("name")
//    public List<Eleve> getByName(@RequestBody String name)


    @PutMapping(value = "{id}")
    public EleveDTO update(@PathVariable Long id,@RequestBody EleveUpdateForm updateForm){
        return eleveService.update(id,updateForm);
    }


}
