package com.example.clubmanager.service.abonnement;


import com.example.clubmanager.models.dto.AbonnementDTO;
import com.example.clubmanager.models.dto.AbonnementUpdateDTO;
import com.example.clubmanager.models.forms.AbonnementCreateForm;
import com.example.clubmanager.models.forms.AbonnementUpdateForm;
import com.example.clubmanager.service.CrudService;

import java.util.List;

public interface AboService extends CrudService<AbonnementDTO, Long, AbonnementCreateForm, AbonnementUpdateForm> {
    List<AbonnementUpdateDTO> getAllforUpdate();
}
