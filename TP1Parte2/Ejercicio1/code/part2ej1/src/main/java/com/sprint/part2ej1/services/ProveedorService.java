package com.sprint.part2ej1.services;

import com.sprint.part2ej1.entities.Proveedor;
import com.sprint.part2ej1.repositories.ProveedorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {


    @Autowired
    private ProveedorRepository proveedorRepository;

    @Transactional
    public void crearProveedor(String cuit) throws Exception{
        try {
            Proveedor user = new Proveedor();
            user.setCuit(cuit);
            proveedorRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error al crear el proveedor: " + e.getMessage());
        }
    }


    @Transactional
    public void modificarProveedor(String idProveedor, String cuit) throws Exception{
        try {
            Optional<Proveedor> prov = proveedorRepository.findById(idProveedor);
            if (prov.isPresent()) {
                Proveedor provAct = prov.get();
                provAct.setCuit(cuit);
                proveedorRepository.save(provAct);
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar proveedor: " + e.getMessage());
        }
    }
    @Transactional
    public void eliminarProveedor(String idProveedor) throws Exception{
        try {
            Proveedor prov = buscarProveedor(idProveedor);
            prov.setEliminado(true);
            proveedorRepository.save(prov);
        } catch (Exception e) {
            throw new Exception("Error al eliminar proveedor: " + e.getMessage());
        }
    }

    public Proveedor buscarProveedor(String idProveedor) throws Exception{
        try {
            Optional<Proveedor> prov = proveedorRepository.findById(idProveedor);
            if (prov.isPresent()) {
                Proveedor provActual = prov.get();
                return provActual;
            } else {
                throw new Exception("Proveedor no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar proveedor: " + e.getMessage());
        }
    }

    @Transactional
    public List<Proveedor> listarProveedoresActivos() throws Exception {
        try {
            return this.proveedorRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
