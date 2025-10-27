package com.sprint.consultorio.services;

import com.sprint.consultorio.entities.FotoPaciente;
import com.sprint.consultorio.repositories.FotoPacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FotoPacienteServiceImpl extends BaseServiceImpl<FotoPaciente, Long> implements FotoPacienteService {

    @Autowired
    private FotoPacienteRepository fotoPacienteRepository;

    @Override
    public FotoPaciente save(FotoPaciente foto) throws Exception {
        return fotoPacienteRepository.save(foto);
    }
}
