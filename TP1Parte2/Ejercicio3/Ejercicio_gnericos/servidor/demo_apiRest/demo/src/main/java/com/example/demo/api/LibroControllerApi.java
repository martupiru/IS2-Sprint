package com.example.demo.api;

import com.example.demo.entities.Autor;
import com.example.demo.entities.Libro;
import com.example.demo.repositories.AutorRepository;
import com.example.demo.services.ErrorServiceException;
import com.example.demo.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/v1/libros")
public class LibroControllerApi extends BaseControllerApi<Libro , Long> {

    public LibroControllerApi(LibroService service) {
        super(service);
    }


}
