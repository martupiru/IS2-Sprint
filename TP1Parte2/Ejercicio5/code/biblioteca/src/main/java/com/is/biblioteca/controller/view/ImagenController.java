
package com.is.biblioteca.controller.view;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.is.biblioteca.business.domain.entity.Usuario;
import com.is.biblioteca.business.logic.service.UsuarioService;

@Controller
@RequestMapping("/imagen")
public class ImagenController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id){
    	
      try {  
    	  
       Usuario usuario = usuarioService.buscarUsuario(id);
        
       byte[] imagen= usuario.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.IMAGE_JPEG);

       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
       
      }catch(Exception e) {
       return null;	  
      } 
    }
}
