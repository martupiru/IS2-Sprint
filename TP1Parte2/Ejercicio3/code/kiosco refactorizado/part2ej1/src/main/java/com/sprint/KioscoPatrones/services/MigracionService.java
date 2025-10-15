package com.sprint.KioscoPatrones.services;

import com.sprint.KioscoPatrones.entities.*;
import com.sprint.KioscoPatrones.repositories.*;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MigracionService {

    @Autowired
    PaisRepository paisRepository;

    @Autowired
    ProvinciaRepository provinciaRepository;

    @Autowired
    DireccionRepository direccionRepository;

    @Autowired
    DepartamentoRepository departamentoRepository;

    @Autowired
    LocalidadRepository localidadRepository;

    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    private ProveedorService proveedorService;

    private static final int CAMPOS_ESPERADOS = 17;
    private static final String SEPARATOR = ";";

    @Transactional
    public int migrarProveedores(MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new Exception("El archivo está vacío.");
        }

        int registrosProcesados = 0;
        List<String> errores = new ArrayList<>();
        int lineNumber = 0; // Para mejor referencia de errores

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            // 1. PROCESAR TODAS LAS LÍNEAS
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                try {
                    String[] fields = line.split(SEPARATOR);

                    // A. Verificar el número correcto de campos
                    if (fields.length < CAMPOS_ESPERADOS) {
                        errores.add("Línea " + lineNumber + " incompleta (" + fields.length + "/" + CAMPOS_ESPERADOS + " campos): " + line);
                        continue;
                    }

                    // --- Mapeo de campos de Proveedor (Índices 0-4) ---
                    String nombre = fields[0].trim();
                    String apellido = fields[1].trim();
                    String telefono = fields[2].trim();
                    String correoElectronico = fields[3].trim();
                    String cuit = fields[4].trim();

                    // --- Mapeo de campos de Dirección (Índices 5-16) ---
                    String calle = fields[5].trim();
                    String numeracion = fields[6].trim();
                    String nombreLocalidad = fields[7].trim();
                    String latitud = fields[11].trim();
                    String longitud = fields[12].trim();
                    String barrio = fields[13].trim();
                    String manzanaPiso = fields[14].trim();
                    String casaDepartamento = fields[15].trim();
                    String referencia = fields[16].trim();


                    // B. Verificar si el proveedor ya existe por CUIT
                    Optional<Proveedor> existingProv = proveedorService.buscarProveedorPorCuit(cuit);
                    if (existingProv.isPresent()) {
                        errores.add("Línea " + lineNumber + ": Proveedor con CUIT " + cuit + " ya existe. Se omite.");
                        continue;
                    }

                    // C. Buscar o crear la Localidad (Necesitas implementar el manejo de País, Provincia, Departamento si Localidad depende de ellos)
                    Localidad localidad = localidadRepository.findByNombre(nombreLocalidad)
                            .orElseGet(() -> {
                                Localidad nuevaLoc = new Localidad();
                                nuevaLoc.setNombre(nombreLocalidad);
                                // Nota: Asigna aquí Departamento/Provincia/País si son obligatorios en Localidad.
                                return localidadRepository.save(nuevaLoc);
                            });

                    // D. Crear y asignar la Dirección
                    Direccion direccion = new Direccion();
                    direccion.setCalle(calle);
                    direccion.setNumeracion(numeracion);
                    direccion.setLocalidad(localidad);
                    direccion.setLatitud(latitud);
                    direccion.setLongitud(longitud);
                    direccion.setBarrio(barrio);
                    direccion.setManzanaPiso(manzanaPiso);
                    direccion.setCasaDepartamento(casaDepartamento);
                    direccion.setReferencia(referencia);
                    direccion.setEliminado(false);

                    // E. Crear y asignar el Proveedor
                    Proveedor proveedor = new Proveedor();
                    proveedor.setNombre(nombre);
                    proveedor.setApellido(apellido);
                    proveedor.setTelefono(telefono);
                    proveedor.setCorreoElectronico(correoElectronico);
                    proveedor.setCuit(cuit);
                    proveedor.setDirecciones(List.of(direccion));
                    proveedor.setEliminado(false);

                    // F. Guardar el Proveedor (y Dirección en cascada)
                    proveedorService.save(proveedor);

                    registrosProcesados++;

                } catch (Exception e) {
                    // G. Capturar y registrar errores específicos de la línea, pero CONTINUAR
                    String errorType = (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) ? "DB Constraint" : "Validation/Service";
                    errores.add("Línea " + lineNumber + " (" + errorType + "): " + e.getMessage());
                }
            }
        } catch (IOException e) {
            // 2. ERROR DE LECTURA/ESCRITURA DEL ARCHIVO (fuera del bucle)
            throw new Exception("Error de lectura/escritura del archivo: " + e.getMessage());
        }


        if (!errores.isEmpty()) {
            String resumenErrores = String.join(" | ", errores.subList(0, Math.min(5, errores.size())));
            // Lanzar una excepción con el resumen para que el controlador lo muestre.
            throw new Exception("Migración finalizada con " + registrosProcesados + " éxitos y " + errores.size() + " errores. Primeros errores: " + resumenErrores);
        }

        return registrosProcesados;
    }
}