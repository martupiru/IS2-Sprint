package com.sprint.KioscoPatrones.services;

import com.sprint.KioscoPatrones.entities.Proveedor;
import com.sprint.KioscoPatrones.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService extends BaseServiceImpl<Proveedor, String> {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private PersonaService personaService;

    @Override
    protected JpaRepository<Proveedor, String> getJpaRepository() {
        return proveedorRepository;
    }

    @Override
    @Transactional
    public List<Proveedor> findAllActivos() throws Exception {
        try {
            return this.proveedorRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void crearProveedorConPersona(String nombre, String apellido, String telefono, String correo, String cuit) throws Exception {
        try {
            personaService.validar(nombre, apellido, telefono, correo);
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombre);
            proveedor.setApellido(apellido);
            proveedor.setTelefono(telefono);
            proveedor.setCorreoElectronico(correo);
            proveedor.setCuit(cuit);
            proveedor.setEliminado(false);
            proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw new Exception("Error al crear proveedor con persona: " + e.getMessage());
        }
    }

    public Optional<Proveedor> buscarProveedorPorCuit(String cuit) {
        return proveedorRepository.findByCuit(cuit);
    }
}