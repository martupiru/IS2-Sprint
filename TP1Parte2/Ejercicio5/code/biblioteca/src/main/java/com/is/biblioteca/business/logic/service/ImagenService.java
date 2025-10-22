
package com.is.biblioteca.business.logic.service;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.is.biblioteca.business.domain.entity.Imagen;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import com.is.biblioteca.business.persistence.repository.ImagenRepository;

@Service
public class ImagenService {
	
    @Autowired
    private ImagenRepository repository;
    
    public void validar(MultipartFile archivo)throws ErrorServiceException{
        
        try {		
      	  
          if(archivo == null || archivo.isEmpty()){
              throw new ErrorServiceException("Debe indicar el nombre");
          }
          
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }   
      }
    
    @Transactional
    public Imagen crearImagen(MultipartFile archivo)throws ErrorServiceException{
    	
        try {	
    	  
    	  validar(archivo);
      
    	  Imagen imagen = new Imagen();
    	  imagen.setId(UUID.randomUUID().toString());
    	  imagen.setMime(archivo.getContentType());
    	  imagen.setNombre(archivo.getName());
    	  imagen.setContenido(archivo.getBytes());
    	  imagen.setEliminado(false);
            
          return repository.save(imagen);
            
            
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }  
 
    }
    
    @Transactional
    public Imagen modificarImagen(String idImagen, MultipartFile archivo)throws ErrorServiceException{
    	
    	try {
    		
    		validar(archivo);

    		Imagen imagen = buscarImagen(idImagen);
    		imagen.setMime(archivo.getContentType());
    		imagen.setNombre(archivo.getName());
    		imagen.setContenido(archivo.getBytes());
            
            return repository.save(imagen);
            
    	} catch (ErrorServiceException e) {
            throw e;
        } catch (Exception ex){
            ex.printStackTrace();
            throw new ErrorServiceException("Error de Sistemas");
        }      

    }
    
    public Imagen buscarImagen(String idImagen) throws ErrorServiceException{

        try {
            
            if (idImagen == null || idImagen.trim().isEmpty()) {
                throw new ErrorServiceException("Debe indicar una imagen");
            }

            Optional<Imagen> optional = repository.findById(idImagen);
            Imagen imagen = null;
            if (optional.isPresent()) {
            	imagen= optional.get();
    			if (imagen.isEliminado()){
                    throw new ErrorServiceException("No se encuentra la imagen indicada");
                }
    		}
            
            return imagen;
            
        } catch (ErrorServiceException ex) {  
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ErrorServiceException("Error de sistema");
        }
    }
    
}
