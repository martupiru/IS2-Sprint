package com.sprint.part2ej1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MigracionService {
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private PaisService paisService;
    @Autowired
    private ProvinciaService provinciaService;
    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private LocalidadService localidadService;


    public int migrarProveedores(MultipartFile file) throws IOException {
        int count = 0;
        int errorCount = 0;
        List<String> errores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                try {
                    procesarLineaProveedor(line);
                    count++;
                } catch (Exception e) {
                    errorCount++;
                    String error = "Línea " + lineNumber + ": " + e.getMessage();
                    errores.add(error);
                    System.err.println(error);
                }
            }
        }

        System.out.println("=== RESUMEN DE MIGRACIÓN ===");
        System.out.println("Proveedores migrados exitosamente: " + count);
        System.out.println("Errores encontrados: " + errorCount);
        if (!errores.isEmpty()) {
            System.out.println("\nDetalles de errores:");
            errores.forEach(System.out::println);
        }

        return count;
    }
    @Transactional
    protected void procesarLineaProveedor(String line) throws Exception {
        // Formato: NOMBRE;APELLIDO;TELEFONO;CORREO;CUIT;CALLE;NÚMERO;LOCALIDAD;DEPARTAMENTO;PROVINCIA;PAIS
        String[] datos = line.split(";", -1);

        if (datos.length < 11) {
            throw new IllegalArgumentException(
                    "Formato incorrecto. Se esperaban 11 campos, se encontraron " + datos.length
            );
        }

        // Extraer datos
        String nombre = datos[0].trim();
        String apellido = datos[1].trim();
        String telefono = datos[2].trim();
        String correo = datos[3].trim();
        String cuit = datos[4].trim();
        String calle = datos[5].trim();
        String numeracion = datos[6].trim();
        String localidadNombre = datos[7].trim();
        String departamentoNombre = datos[8].trim();
        String provinciaNombre = datos[9].trim();
        String paisNombre = datos[10].trim();

        // Validaciones básicas de formato
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() ||
                correo.isEmpty() || cuit.isEmpty()) {
            throw new IllegalArgumentException("Los datos del proveedor están incompletos");
        }

        if (calle.isEmpty() || numeracion.isEmpty() || localidadNombre.isEmpty() ||
                departamentoNombre.isEmpty() || provinciaNombre.isEmpty() || paisNombre.isEmpty()) {
            throw new IllegalArgumentException("Los datos de dirección están incompletos");
        }

        // hacer todo en servicios de las entidades
        var pais = paisService.buscarOCrearPais(paisNombre);
        var provincia = provinciaService.buscarOCrearProvincia(provinciaNombre, pais.getId());
        var departamento = departamentoService.buscarOCrearDepartamento(departamentoNombre, provincia.getId());
        var localidad = localidadService.buscarOCrearLocalidad(localidadNombre, departamento.getId());

        proveedorService.crearProveedorConDireccion(
                nombre, apellido, telefono, correo, cuit,
                calle, numeracion, localidad.getId()
        );
    }
}