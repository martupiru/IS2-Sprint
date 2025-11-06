package com.sprint.ejercicio2b.services;

import com.sprint.ejercicio2b.entities.Domicilio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DomicilioService extends BaseService<Domicilio, Long> {
    List<Domicilio> searchByCalle(String calle) throws Exception;
    Page<Domicilio> searchByCalle(String calle, Pageable pageable) throws Exception;
    List<Domicilio> searchByLocalidad(String localidad) throws Exception;
    Page<Domicilio> searchByLocalidad(String localidad, Pageable pageable) throws Exception;
}
