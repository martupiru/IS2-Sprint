package com.is.biblioteca.patrones.Facade;

import com.is.biblioteca.business.domain.entity.Autor;
import com.is.biblioteca.business.domain.entity.Editorial;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import com.is.biblioteca.business.logic.service.AutorService;
import com.is.biblioteca.business.logic.service.EditorialService;
import com.is.biblioteca.business.logic.service.ImagenService;
import com.is.biblioteca.business.logic.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BibliotecaFacade {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private ImagenService imagenService;

    /**
     * Registra un libro en la biblioteca encapsulando toda la lógica de servicios internos.
     *
     * @param archivo     Portada del libro
     * @param isbn        ISBN del libro
     * @param titulo      Título del libro
     * @param ejemplares  Cantidad de ejemplares
     * @param idAutor     ID del autor
     * @param idEditorial ID de la editorial
     * @throws ErrorServiceException si ocurre algún error en la validación o creación
     */

    public void registrarLibro(MultipartFile archivo, Long isbn, String titulo, Integer ejemplares,
                               String idAutor, String idEditorial) throws ErrorServiceException {

        Autor autor = autorService.buscarAutor(idAutor);

        Editorial editorial = editorialService.buscarEditorial(idEditorial);

        libroService.crearLibro(archivo, isbn, titulo, ejemplares, autor.getId(), editorial.getId());
    }
}