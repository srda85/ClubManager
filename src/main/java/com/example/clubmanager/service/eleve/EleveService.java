package com.example.clubmanager.service.eleve;


import com.example.clubmanager.models.dto.EleveDTO;
import com.example.clubmanager.models.dto.EleveReducedDataDto;
import com.example.clubmanager.models.forms.EleveCreateForm;
import com.example.clubmanager.models.forms.EleveUpdateForm;
import com.example.clubmanager.service.CrudService;

import java.util.List;


public interface EleveService extends CrudService<EleveDTO , Long, EleveCreateForm, EleveUpdateForm> {

    public List<EleveReducedDataDto> getAllforTab();
}
