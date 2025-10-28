package com.gimnasio.gimnasio.services;

import com.gimnasio.gimnasio.entities.CuotaMensual;
import com.gimnasio.gimnasio.entities.DetalleFactura;
import com.gimnasio.gimnasio.entities.Factura;
import com.gimnasio.gimnasio.entities.FormaDePago;
import com.gimnasio.gimnasio.enumerations.EstadoFactura;
import com.gimnasio.gimnasio.repositories.DetalleFacturaRepository;
import com.gimnasio.gimnasio.repositories.FacturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    public Long obtenerUltimoNumeroFactura() {
        Long ultimo = facturaRepository.findUltimoNumeroFactura();
        return (ultimo == null) ? 0L : ultimo;
    }


    public void crearFactura(Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado, List<DetalleFactura> detalle, FormaDePago formaDePago) throws Exception{
        try {
            validar(numeroFactura, fechaFactura, totalPagado, estado);
            Factura factura = new Factura();
            Long UltimoNumFactura =  obtenerUltimoNumeroFactura();
            factura.setNumeroFactura(UltimoNumFactura + 1);
            factura.setFechaFactura(fechaFactura);
            factura.setTotalPagado(totalPagado);
            factura.setEstadoFactura(estado);
            factura.setFormaDePago(formaDePago);
            factura.setDetalleFactura(new ArrayList<>());
            factura.setDetalleFactura(detalle);
            facturaRepository.save(factura);
        } catch (Exception e) {
            throw new Exception("Error al crear factura: " + e.getMessage());
        }
    }

    public void validar(Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado) throws Exception{

    }

    public void modificarFactura(String id, Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado, Collection<DetalleFactura> detalle) throws Exception{
        try {
            validar(numeroFactura, fechaFactura, totalPagado, estado);
            Optional<Factura> factura = facturaRepository.findById(id);
            if (factura.isPresent()) {
                Factura facturaActual = factura.get();
                // Se modifica numFactura?
                facturaActual.setFechaFactura(fechaFactura);
                facturaActual.setTotalPagado(totalPagado);
                facturaActual.setEstadoFactura(estado);
                facturaActual.getDetalleFactura().clear();
                facturaRepository.save(facturaActual);
            } else {
                throw new Exception("Factura no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al modificar factura: " + e.getMessage());
        }
    }

    public void eliminarFactura(String id) throws Exception{
        try {
            Factura factura = buscarFactura(id);
            factura.setEliminado(true);
            facturaRepository.save(factura);
        } catch (Exception e) {
            throw new Exception("Error al eliminar factura: " + e.getMessage());
        }
    }

    public Factura buscarFactura(String id) throws Exception{
        try {
            Optional<Factura> factura = facturaRepository.findById(id);
            if (factura.isPresent()) {
                Factura facturaActual = factura.get();
                return facturaActual;
            } else {
                throw new Exception("Factura no encontrada");
            }
        } catch (Exception e) {
            throw new Exception("Error al buscar factura: " + e.getMessage());
        }
    }

    @Transactional
    public List<Factura> listarFacturas() throws Exception {
        try {
            List<Factura> entities = this.facturaRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Factura> listarFacturasActivas() throws Exception {
        try {
            return this.facturaRepository.findAllByEliminadoFalse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public DetalleFactura crearDetalleFactura(String id, CuotaMensual cuota){
        DetalleFactura detalleFactura = new DetalleFactura();
        detalleFactura.setEliminado(false);
        detalleFactura.setCuotaMensual(cuota);
        detalleFacturaRepository.save(detalleFactura);
        return detalleFactura;
    }

    public Factura crearFactura2(Long numeroFactura, Date fechaFactura, double totalPagado, EstadoFactura estado, List<DetalleFactura> detalle, FormaDePago formaDePago) throws Exception{
        try {
            validar(numeroFactura, fechaFactura, totalPagado, estado);
            Factura factura = new Factura();
            Long UltimoNumFactura =  obtenerUltimoNumeroFactura();
            factura.setNumeroFactura(UltimoNumFactura + 1);
            factura.setFechaFactura(fechaFactura);
            factura.setTotalPagado(totalPagado);
            factura.setEstadoFactura(estado);
            factura.setFormaDePago(formaDePago);
            factura.setDetalleFactura(new ArrayList<>());
            factura.setDetalleFactura(detalle);
            facturaRepository.save(factura);
            return factura;
        } catch (Exception e) {
            throw new Exception("Error al crear factura: " + e.getMessage());
        }
    }

}
