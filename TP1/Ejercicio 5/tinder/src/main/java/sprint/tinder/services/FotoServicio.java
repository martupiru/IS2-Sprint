package sprint.tinder.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sprint.tinder.entities.Foto;
import sprint.tinder.errors.ErrorServicio;
import sprint.tinder.repositories.FotoRepositorio;

import java.util.Optional;

@Service
public class FotoServicio {
    @Autowired
    private FotoRepositorio fotoRepositorio;

    @Transactional //Si el metodo se ejecuta sin largar excepciones, entonces se hace un commit a la base de datos y se aplican todos los cambios. Si hay una excepcion, se hace un rollback y no se aplica nada a la BD
    public Foto guardar (MultipartFile archivo) throws ErrorServicio {  // MultipartFile es donde se va a almacenar el archivo cuando la cargue el usuario
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                foto.setMime(archivo.getContentType()); // Nos devuelve el tipo mime del archivo adjunto, entonces lo seteamos en el atributo mime
                foto.setNombre(archivo.getName());
                foto.setConenido(archivo.getBytes()); //El metodo getBytes puede tirar una excepcion, asi que tenemos que poner un try-catch por esto
                return fotoRepositorio.save(foto); //Devolvemos la foto persistida
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null; //Devolvemos null si el archivo está vacío o si hubo un problema leyendo el contenido del archivo

    }

    @Transactional
    public Foto actualizar (String idFoto, MultipartFile archivo) throws ErrorServicio {
        if (archivo != null) {
            try {
                Foto foto = new Foto();
                if(idFoto != null){
                    Optional<Foto> respuesta = fotoRepositorio.findById(idFoto);
                    if(respuesta.isPresent()){ // Trae una foto de la base de datos usando ese id
                        foto =  respuesta.get();
                    } //Agregar else
                }
                foto.setMime(archivo.getContentType()); // Nos devuelve el tipo mime del archivo adjunto, entonces lo seteamos en el atributo mime
                foto.setNombre(archivo.getName());
                foto.setConenido(archivo.getBytes()); //El metodo getBytes puede tirar una excepcion, asi que tenemos que poner un try-catch por esto
                return fotoRepositorio.save(foto); //Devolvemos la foto persistida
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null; //Devolvemos null si el archivo está vacío o si hubo un problema leyendo el contenido del archivo
    }
}
