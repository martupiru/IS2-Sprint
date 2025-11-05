package com.sprint.ejercicio2b.services;

import com.sprint.ejercicio2b.entities.Domicilio;
import com.sprint.ejercicio2b.repositories.BaseRepository;
import com.sprint.ejercicio2b.repositories.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomicilioServiceImpl extends BaseServiceImpl<Domicilio, Long> implements DomicilioService {

    @Autowired
    private DomicilioRepository domicilioRepository;

    public DomicilioServiceImpl(BaseRepository<Domicilio, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Domicilio> searchByCalle(String calle) throws Exception {
        try {
            return domicilioRepository.searchByCalle(calle);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Domicilio> searchByCalle(String calle, Pageable pageable) throws Exception {
        try {
            return domicilioRepository.searchByCalle(calle, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Domicilio> searchByLocalidad(String localidad) throws Exception {
        try {
            return domicilioRepository.searchByLocalidad(localidad);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Domicilio> searchByLocalidad(String localidad, Pageable pageable) throws Exception {
        try {
            return domicilioRepository.searchByLocalidad(localidad, pageable);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
