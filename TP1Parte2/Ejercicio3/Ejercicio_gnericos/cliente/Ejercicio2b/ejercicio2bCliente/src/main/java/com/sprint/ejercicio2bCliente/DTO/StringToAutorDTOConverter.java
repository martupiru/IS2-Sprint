package com.sprint.ejercicio2bCliente.DTO;

import org.springframework.core.convert.converter.Converter;
import com.sprint.ejercicio2bCliente.services.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StringToAutorDTOConverter implements Converter<String, AutorDTO> {

    @Autowired
    private AutorService autorService;

    @Override
    public AutorDTO convert(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return autorService.obtener(Long.parseLong(id));
    }

}
