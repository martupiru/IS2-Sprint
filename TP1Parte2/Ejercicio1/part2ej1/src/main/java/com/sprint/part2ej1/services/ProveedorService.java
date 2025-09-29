package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Proveedor;
import com.sprint.part2ej1.repositories.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {


    @Autowired
    private ProveedorRepository proveedorRepository;


    @Transactional
    public List<Proveedor> listarProveedoresActivos() throws Exception {
        try {
            return this.proveedorRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
