package com.sprint.tinder.services;

import com.sprint.tinder.entities.Foto;
import com.sprint.tinder.errors.ErrorServicio;
import com.sprint.tinder.repositories.FotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepositorio;

    @Transactional
    //Si el metodo se ejecuta sin largar excepciones, entonces se hace un commit a la base de datos y se aplican todos los cambios. Si hay una excepcion, se hace un rollback y no se aplica nada a la BD
    public Foto guardar (MultipartFile archivo) throws ErrorServicio {  // MultipartFile es donde se va a almacenar el archivo cuando la cargue el usuario
        if (archivo == null || archivo.isEmpty()) {
            // no hay archivo: devolver null (o lanzar ErrorServicio si preferís)
            return null;
        }

        try {
            // para debuggear
            System.out.println("FotoServicio.guardar -> archivo.isEmpty=" + archivo.isEmpty()
                    + ", originalName=" + archivo.getOriginalFilename()
                    + ", parameterName=" + archivo.getName()
                    + ", contentType=" + archivo.getContentType()
                    + ", size=" + archivo.getSize());

            Foto foto = new Foto();
            foto.setMime(archivo.getContentType() != null ? archivo.getContentType() : "application/octet-stream");
            // usar el nombre original del fichero y no getNAme()
            foto.setNombre(archivo.getOriginalFilename());
            foto.setContenido(archivo.getBytes());
            foto.setEliminado(false);
            Foto guardada = fotoRepositorio.save(foto);

            // DEBUG: confirmar guardado y tamaño del blob
            System.out.println("Foto guardada: id=" + guardada.getId()
                    + " bytes=" + (guardada.getContenido() != null ? guardada.getContenido().length : "null"));

            return guardada;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error al guardar la foto: " + e.getMessage());
        }
    }

    @Transactional
    public Foto actualizar (String idFoto, MultipartFile archivo) throws ErrorServicio {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }
        try {
            Foto foto;
            if (idFoto != null) {
                Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                foto = respuesta.orElse(new Foto());
            } else {
                foto = new Foto();
            }

            foto.setMime(archivo.getContentType() != null ? archivo.getContentType() : "application/octet-stream");
            foto.setNombre(archivo.getOriginalFilename());
            foto.setContenido(archivo.getBytes());
            foto.setEliminado(false);

            Foto guardada = fotoRepositorio.save(foto);
            System.out.println("Foto actualizada: id=" + guardada.getId()
                    + " bytes=" + (guardada.getContenido() != null ? guardada.getContenido().length : "null"));
            return guardada;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErrorServicio("Error al actualizar la foto: " + e.getMessage());
        }
    }

    public void validar(MultipartFile archivo)throws ErrorServicio{
        try {
            if(archivo == null || archivo.isEmpty()){
                throw new ErrorServicio("Debe indicar el archivo");
            }
        } catch (ErrorServicio e) {
            throw e;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServicio("Error");
        }
    }

    public Foto buscarFoto(String idFoto) throws ErrorServicio{

        try {
            if (idFoto == null || idFoto.trim().isEmpty()) {
                throw new ErrorServicio("Debe indicar la foto");
            }
            Optional<Foto> optional = fotoRepositorio.findById(idFoto);
            Foto foto = null;
            if (optional.isPresent()) {
                foto= optional.get();
                if (foto.isEliminado()){
                    throw new ErrorServicio("No se encuentra la foto indicada");
                }
            }
            return foto;
        } catch (ErrorServicio ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServicio("Error de sistema");
        }
    }
}
