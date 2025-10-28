package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Direccion;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.Sucursal;
import com.gimnasio.gimnasio.entities.Usuario;
import com.gimnasio.gimnasio.enumerations.TipoDocumento;
import com.gimnasio.gimnasio.repositories.DireccionRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Long obtenerUltimoNumeroSocio() {
        if (socioRepository.contarSocios() == 0) {
            return 0L;
        }
        Long ultimo = socioRepository.findUltimoNumeroSocio();
        return (ultimo == null) ? 0L : ultimo;
    }

    @Transactional
    public void crearSocio(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Direccion direccion, Sucursal sucursal) throws Exception{
        try {
            Socio socio = new Socio();
            socio.setNombre(nombre);
            socio.setApellido(apellido);
            socio.setFechaNacimiento(fechaNacimiento);
            socio.setTipoDocumento(tipoDocumento);
            socio.setNumeroDocumento(numeroDocumento);
            socio.setTelefono(telefono);
            socio.setCorreoElectronico(correoElectronico);
            Long ultimo = obtenerUltimoNumeroSocio();
            socio.setNumeroSocio(ultimo + 1);
            Direccion direccionGuardada = direccionRepository.save(direccion);

            socio.setDireccion(direccion);
            socio.setSucursal(sucursal);
            socio.setEliminado(false);
            socioRepository.save(socio);
        } catch (Exception e) {
            throw new Exception("Error al crear socio: " + e.getMessage());
        }
    }

    @Transactional
    public Socio crearSocioObj(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Direccion direccion, Sucursal sucursal) throws Exception{
        try {
            Socio socio = new Socio();
            socio.setNombre(nombre);
            socio.setApellido(apellido);
            socio.setFechaNacimiento(fechaNacimiento);
            socio.setTipoDocumento(tipoDocumento);
            socio.setNumeroDocumento(numeroDocumento);
            socio.setTelefono(telefono);
            socio.setCorreoElectronico(correoElectronico);
            Long ultimo = obtenerUltimoNumeroSocio();
            socio.setNumeroSocio(ultimo + 1);
            Direccion direccionGuardada = direccionRepository.save(direccion);

            socio.setDireccion(direccion);
            socio.setSucursal(sucursal);
            socio.setEliminado(false);
            socioRepository.save(socio);
            return socio;
        } catch (Exception e) {
            throw new Exception("Error al crear socio: " + e.getMessage());
        }
    }

    @Transactional
    public void modificarSocio(String nombre, String apellido, LocalDate fechaNacimiento, TipoDocumento tipoDocumento, String numeroDocumento, String telefono, String correoElectronico, Long numeroSocio, Direccion direccion, Sucursal sucursal) throws Exception{
        try {
            Optional<Socio> optSocio = socioRepository.findById(numeroSocio);
            if (optSocio.isPresent()) {
                Socio socio = optSocio.get();
                socio.setNombre(nombre);
                socio.setApellido(apellido);
                socio.setFechaNacimiento(fechaNacimiento);
                socio.setTipoDocumento(tipoDocumento);
                socio.setNumeroDocumento(numeroDocumento);
                socio.setTelefono(telefono);
                socio.setCorreoElectronico(correoElectronico);
                socio.setDireccion(direccion);
                socio.setSucursal(sucursal);
                socioRepository.save(socio);
            } else {
                throw new Exception("Socio no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar socio: " + e.getMessage());
        }
    }


    @Transactional
    public boolean deleteById(Long numSocio) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findByNumeroSocio(numSocio);
            if (opt.isPresent()) {
                Socio socio = opt.get();
                socio.setEliminado(true);
                this.socioRepository.save(socio);

                Optional<Usuario> optUsuario = usuarioRepository.findByNombreUsuario(socio.getCorreoElectronico());
                if (optUsuario.isPresent()) {
                    Usuario usuario = optUsuario.get();
                    usuario.setEliminado(true);
                    usuarioRepository.save(usuario);
                }

            } else {
                throw new Exception("Socio no encontrado");
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    public Socio buscarSocio(Long idSocio) throws Exception{
        try {
            Optional<Socio> socio = socioRepository.findByNumeroSocio(idSocio);
            if (socio.isPresent()) {
                Socio socioAct = socio.get();
                return socioAct;
            } else {
                throw new Exception("Socio no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar socio: " + e.getMessage());
        }
    }

    @Transactional //Creo que este tipo de métodos no van con Transactional
    public List<Socio> findAll() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Socio findById(Long numSocio) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findByNumeroSocio(numSocio);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }



    /*   Métodos nuevos   */

    @Transactional
    public List<Socio> findAllByEliminadoFalse() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAllByEliminadoFalse();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Socio> findAllByEliminadoTrue() throws Exception {
        try {
            List<Socio> entities = this.socioRepository.findAllByEliminadoTrue();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Socio findByIdAndEliminadoFalse(String id) throws Exception {
        try {
            Optional<Socio> opt = this.socioRepository.findByIdAndEliminadoFalse(id);
            return opt.orElse(null);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Socio> obtenerCumpleanosProximos30Dias() {
        LocalDate hoy = LocalDate.now();
        LocalDate dentroDe30 = hoy.plusDays(30);

        List<Socio> todosLosSocios = socioRepository.findAllNoEliminados();

        return todosLosSocios.stream()
                .filter(s -> {
                    LocalDate fn = s.getFechaNacimiento();
                    LocalDate fnEsteAno = fn.withYear(hoy.getYear());
                    return !fnEsteAno.isBefore(hoy) && !fnEsteAno.isAfter(dentroDe30);
                })
                .toList();
    }

}

// falta asociarSocioUsuario