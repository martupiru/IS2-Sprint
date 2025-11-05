package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.Rutina;
import com.gimnasio.gimnasio.repositories.DetalleRutinaRepository;
import com.gimnasio.gimnasio.repositories.EmpleadoRepository;
import com.gimnasio.gimnasio.repositories.RutinaRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import com.gimnasio.gimnasio.services.ServicioBase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import com.gimnasio.gimnasio.entities.DetalleRutina;
import com.gimnasio.gimnasio.entities.Empleado;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.enumerations.EstadoDetalleRutina;
import com.gimnasio.gimnasio.enumerations.EstadoRutina;

@Service
public class RutinaService {

    @Autowired
    private RutinaRepository rutinaRepository;

    @Autowired
    private DetalleRutinaRepository detalleRutinaRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Transactional
    public void crearRutina(String idProfesor, String idSocio, Date fechaInicio, Date fechaFinalizacion, Collection<DetalleRutina> detalle) throws Exception {
        try {
            validar(idProfesor, idSocio, fechaInicio, fechaFinalizacion, detalle);
            Rutina rutina = new Rutina();
            rutina.setFechaInicio(fechaInicio);
            rutina.setFechaFinalizacion(fechaFinalizacion);
            rutina.setEstadoRutina(EstadoRutina.EN_PROCESO);
            rutina.setEliminado(false);
            Socio socio = buscarSocio(idSocio);
            Empleado empleado = buscarEmpleado(idProfesor);
            rutina.setSocio(socio);
            rutina.setEmpleado(empleado);
            rutina.setDetalleRutinas(new ArrayList<>());
//            rutinaRepository.save(rutina);
            for (DetalleRutina d : detalle) {
                d.setRutina(rutina);
                rutina.getDetalleRutinas().add(d);
            }
            rutinaRepository.save(rutina);
        } catch (Exception e) {
            throw new Exception("Error al crear rutina: " + e.getMessage());
        }
    }

    public void validar(String idProfesor, String idSocio, Date fechaInicio, Date fechaFinalizacion, Collection<DetalleRutina> detalle) throws Exception {
        if (idProfesor == null || idProfesor.trim().isEmpty()) {
            throw new Exception("El profesor es requerido");
        }
        if (idSocio == null || idSocio.trim().isEmpty()) {
            throw new Exception("El socio es requerido");
        }
        if (fechaInicio == null || fechaFinalizacion == null) {
            throw new Exception("Las fechas son requeridas");
        }

        // validacion fechas que no se hacen en la entidad
        Date hoy = new Date();
        if (fechaInicio.before(hoy)) {
            throw new Exception("La fecha de inicio no puede ser anterior a hoy");
        }
        if (fechaFinalizacion.before(fechaInicio)) {
            throw new Exception("La fecha de finalización debe ser igual o posterior a la fecha de inicio");
        }

        if (detalle == null || detalle.isEmpty()) {
            throw new Exception("Debe agregar al menos un detalle de rutina");
        }
        if (socioRepository.findByIdAndEliminadoFalse(idSocio).isEmpty()) {
            throw new Exception("Socio no encontrado");
        }
        if (empleadoRepository.findById(idProfesor).isEmpty()) {
            throw new Exception("Profesor no encontrado");
        }
    }

    @Transactional
    public void modificarRutina(String id, String idProfesor, String idSocio, Date fechaInicio, Date fechaFinalizacion, Collection<DetalleRutina> detalle, EstadoRutina estado) throws Exception {
        try {
            validar(idProfesor, idSocio, fechaInicio, fechaFinalizacion, detalle);
            Optional<Rutina> rutinaOpt = rutinaRepository.findById(id);
            if (rutinaOpt.isPresent()) {
                Rutina rutina = rutinaOpt.get();
                rutina.setFechaInicio(fechaInicio);
                rutina.setFechaFinalizacion(fechaFinalizacion);
                rutina.setSocio(buscarSocio(idSocio));
                rutina.setEmpleado(buscarEmpleado(idProfesor));
                rutina.setEstadoRutina(estado);
                rutina.getDetalleRutinas().clear();
                for (DetalleRutina d : detalle) {
                    d.setRutina(rutina);
                    rutina.getDetalleRutinas().add(d);
                }
                rutinaRepository.save(rutina);
            } else {
                throw new Exception("Rutina no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar rutina: " + e.getMessage());
        }
    }

    public Rutina buscarRutina(String id) throws Exception {
        try {
            Optional<Rutina> rutina = rutinaRepository.findById(id);
            if (rutina.isPresent()) {
                return rutina.get();
            } else {
                throw new Exception("Rutina no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar rutina: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarRutina(String id) throws Exception {
        try {
            Rutina rutina = buscarRutina(id);
            rutina.setEliminado(true);
            rutinaRepository.save(rutina);
        } catch (Exception e) {
            throw new Exception("Error al eliminar rutina: " + e.getMessage());
        }
    }

    @Transactional
    public void modificarEstadoRutina(String id, EstadoRutina estadoRutina) throws Exception {
        try {
            Rutina rutina = buscarRutina(id);
            rutina.setEstadoRutina(estadoRutina);
            rutinaRepository.save(rutina);
        } catch (Exception e) {
            throw new Exception("Error al modificar estado de rutina: " + e.getMessage());
        }
    }

    @Transactional
    public List<Rutina> listarRutina() throws Exception {
        try {
            return rutinaRepository.findAll();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Rutina> listarRutinaActivo() throws Exception {
        try {
            return rutinaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Rutina buscarRutinaActual(String id) throws Exception {
        try {
            Optional<Rutina> rutina = rutinaRepository.findRutinaActualBySocio(id);
            if (rutina.isPresent()) {
                return rutina.get();
            } else {
                throw new Exception("Rutina activa no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar rutina actual: " + e.getMessage());
        }
    }

    @Transactional
    public DetalleRutina crearDetalleRutina(Date fecha, String actividad) throws Exception {
        try {
            DetalleRutina detalle = new DetalleRutina();
            detalle.setFecha(fecha);
            detalle.setActividad(actividad);
            detalle.setEstadoDetalleRutina(EstadoDetalleRutina.SIN_REALIZAR);
            detalle.setEliminado(false);
            return detalleRutinaRepository.save(detalle);
        } catch (Exception e) {
            throw new Exception("Error al crear detalle rutina: " + e.getMessage());
        }
    }

    public DetalleRutina buscarDetalleRutina(String idDetalleRutina) throws Exception {
        try {
            Optional<DetalleRutina> detalle = detalleRutinaRepository.findById(idDetalleRutina);
            if (detalle.isPresent()) {
                return detalle.get();
            } else {
                throw new Exception("Detalle rutina no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar detalle rutina: " + e.getMessage());
        }
    }

    @Transactional
    public DetalleRutina modificarDetalleRutina(String idDetalleRutina, Date fecha, String actividad) throws Exception {
        try {
            DetalleRutina detalle = buscarDetalleRutina(idDetalleRutina);
            detalle.setFecha(fecha);
            detalle.setActividad(actividad);
            return detalleRutinaRepository.save(detalle);
        } catch (Exception e) {
            throw new Exception("Error al modificar detalle rutina: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarDetalleRutina(String idDetalleRutina) throws Exception {
        try {
            DetalleRutina detalle = buscarDetalleRutina(idDetalleRutina);
            detalle.setEliminado(true);
            detalleRutinaRepository.save(detalle);
        } catch (Exception e) {
            throw new Exception("Error al eliminar detalle rutina: " + e.getMessage());
        }
    }

    @Transactional
    public void modificarEstadoDetalleRutina(String idDetalleRutina, EstadoDetalleRutina estadoDetalleRutina) throws Exception {
        try {
            DetalleRutina detalle = buscarDetalleRutina(idDetalleRutina);
            detalle.setEstadoDetalleRutina(estadoDetalleRutina);
            detalleRutinaRepository.save(detalle);
        } catch (Exception e) {
            throw new Exception("Error al modificar estado detalle rutina: " + e.getMessage());
        }
    }

    private Socio buscarSocio(String idSocio) throws Exception {
        Optional<Socio> socio = socioRepository.findByIdAndEliminadoFalse(idSocio);
        if (socio.isPresent()) {
            return socio.get();
        } else {
            throw new Exception("Socio no encontrado");
        }
    }

    private Empleado buscarEmpleado(String idProfesor) throws Exception {
        Optional<Empleado> empleado = empleadoRepository.findById(idProfesor);
        if (empleado.isPresent()) {
            return empleado.get();
        } else {
            throw new Exception("Profesor no encontrado");
        }
    }

    public List<Rutina> listarRutinasPorSocio(String idSocio) throws Exception {
        try {
            return rutinaRepository.findBySocioIdAndEliminadoFalse(idSocio);
        } catch (Exception e) {
            throw new Exception("Error al listar rutinas por socio: " + e.getMessage());
        }
    }
}