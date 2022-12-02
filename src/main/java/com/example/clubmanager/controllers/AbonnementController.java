package com.example.clubmanager.controllers;

import com.example.clubmanager.models.dto.AbonnementDTO;
import com.example.clubmanager.models.dto.AbonnementUpdateDTO;
import com.example.clubmanager.models.forms.AbonnementCreateForm;
import com.example.clubmanager.models.forms.AbonnementUpdateForm;
import com.example.clubmanager.service.abonnement.AboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/ClubManager/abonnements")
@Secured("ROLE_ADMIN")
public class AbonnementController {

    @Autowired
    AboService service;

    @GetMapping("all")
    public List<AbonnementDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("allForUpdate")
    public List<AbonnementUpdateDTO>getAllforUpdate(){
        return service.getAllforUpdate();
    }


    @PostMapping
    public AbonnementDTO create(@RequestBody final AbonnementCreateForm form){
        return service.create(form);
    }

    @GetMapping
    @RequestMapping("{id}")
    public  AbonnementDTO getById(@PathVariable Long id){
        return service.getOne(id);
    }

    @DeleteMapping(value = "{id}")
    //@ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping(value = "{id}")
    public AbonnementDTO update(@PathVariable Long id,@RequestBody AbonnementUpdateForm form){
        return service.update(id,form);
    }





}
