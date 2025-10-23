package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.Socio;
import com.gimnasio.gimnasio.entities.ValorCuota;
import com.gimnasio.gimnasio.enumerations.Meses;
import com.gimnasio.gimnasio.enumerations.EstadoCuotaMensual;
import com.gimnasio.gimnasio.repositories.CuotaMensualRepository;
import com.gimnasio.gimnasio.repositories.SocioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuotaMensualService {
    @Autowired
    private CuotaMensualRepository cuotaMensualRepository;

    @Autowired
    private SocioRepository socioRepository;

    public void crearCuotaMensual(String idSocio, Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento, ValorCuota valorCuota) throws Exception{
        try {
            validar(mes, anio, estado, fechaVencimiento);
            Socio socio = buscarSocio(idSocio);

            CuotaMensual cuota = new CuotaMensual();
            cuota.setMes(mes);
            cuota.setAnio(anio);
            cuota.setEstado(estado);
            cuota.setFechaVencimiento(fechaVencimiento);
            cuota.setSocio(socio);
            cuota.setEliminado(false);
            cuota.setValorCuota(valorCuota);
            cuotaMensualRepository.save(cuota);
        } catch (Exception e) {
            throw new Exception("Error al crear cuota: " + e.getMessage());
        }
    }

    public void validar (Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento) throws Exception{
        if(mes == null){
            throw new Exception("El tipo de mensaje es obligatorio");
        }
        if(anio <= 2000){
            throw new Exception("El año debe ser mayor o igual a 2000");
        }
        if(estado == null){
            throw new Exception("El estado es obligatorio");
        }
        if(fechaVencimiento == null){
            throw new Exception("La fecha de vencimiento es obligatoria");
        }
    }

    public void modificarCuotaMensual(String id, String idSocio, Meses mes, long anio, EstadoCuotaMensual estado, Date fechaVencimiento) throws Exception{
        try {
            validar(mes, anio, estado, fechaVencimiento);
            Optional<CuotaMensual> cuotaMensual = cuotaMensualRepository.findById(id);
            if (cuotaMensual.isPresent()) {
                Socio socio = buscarSocio(idSocio);
                CuotaMensual cuotaActual = cuotaMensual.get();
                cuotaActual.setMes(mes);
                cuotaActual.setAnio(anio);
                cuotaActual.setEstado(estado);
                cuotaActual.setFechaVencimiento(fechaVencimiento);
                cuotaActual.setSocio(socio);
                cuotaMensualRepository.save(cuotaActual);
            } else {
                throw new Exception("Cuota no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar cuota: " + e.getMessage());
        }
    }

    public void eliminarCuotaMensual(String id) throws Exception{
        try {
            CuotaMensual cuota = buscarCuotaMensual(id);
            cuota.setEliminado(true);
            cuotaMensualRepository.save(cuota);
        } catch (Exception e) {
            throw new Exception("Error al eliminar cuota: " + e.getMessage());
        }
    }

    public CuotaMensual buscarCuotaMensual(String id) throws Exception{
        try {
            Optional<CuotaMensual> cuota = cuotaMensualRepository.findById(id);
            if (cuota.isPresent()) {
                CuotaMensual cuotaActual = cuota.get();
                return cuotaActual;
            } else {
                throw new Exception("Cuota no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar cuota: " + e.getMessage());
        }
    }

    public Socio buscarSocio(String idSocio) throws Exception{
        try {
            Optional<Socio> socio = socioRepository.findByIdAndEliminadoFalse(idSocio);
            if (socio.isPresent()) {
                return socio.get();
            } else {
                throw new Exception("Socio no encontrado");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar socio: " + e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotas() throws Exception {
        try {
            List<CuotaMensual> entities = this.cuotaMensualRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotasActivas() throws Exception {
        try {
            return this.cuotaMensualRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<CuotaMensual> listarCuotaMensualPorEstado(EstadoCuotaMensual estado) throws Exception {
        try {
            return this.cuotaMensualRepository.findByEstadoAndEliminadoFalse(estado);
        } catch (Exception e) {
            throw new Exception("Error al listar cuotas por estado: " + e.getMessage());
        }
    }

    // Desde el controlador se transforma mes y anio en fechaDesde
    @Transactional
    public List<CuotaMensual> listarCuotaMensualPorFecha(Date fechaDesde, Date fechaHasta) throws Exception {
        try {
            if (fechaDesde == null || fechaHasta == null) {
                throw new Exception("Las fechas no pueden ser nulas");
            }
            if (fechaDesde.after(fechaHasta)) {
                throw new Exception("La fecha desde no puede ser después de la fecha hasta");
            }
            return this.cuotaMensualRepository.findByFechaVencimientoBetweenAndEliminadoFalse(fechaDesde, fechaHasta);
        } catch (Exception e) {
            throw new Exception("Error al listar cuotas por fecha: " + e.getMessage());
        }
    }

    public List<CuotaMensual> listarCuotasPorSocio(Long numSocio) {
        return cuotaMensualRepository.findBySocioNumeroSocioAndEliminadoFalse(numSocio);
    }

    public List<CuotaMensual> listarCuotasAdeudadasPorSocio(Socio socio) {
        return cuotaMensualRepository.findBySocioAndEstadoAndEliminadoFalse(socio, EstadoCuotaMensual.ADEUDADA);
    }

    public List<CuotaMensual> listarCuotasPorMesAnioYSocios(Meses mes, int anio, List<Socio> socios) {
        return cuotaMensualRepository.findByMesAndAnioAndSocioInAndEliminadoFalse(mes, (long) anio, socios);
    }

    public boolean existeCuotaPorMesAnio(Meses mes, int anio) {
        List<CuotaMensual> cuotas = cuotaMensualRepository.findByMesAndAnioAndEliminadoFalse(mes, (long) anio);
        return cuotas != null && !cuotas.isEmpty();
    }

}
