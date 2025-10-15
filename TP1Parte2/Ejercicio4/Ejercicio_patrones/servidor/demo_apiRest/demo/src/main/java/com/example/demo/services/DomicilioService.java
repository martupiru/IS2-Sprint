package com.example.demo.services;

import com.example.demo.entities.Domicilio;
import com.example.demo.repositories.DomicilioRepository;
import org.springframework.stereotype.Service;

@Service
public class DomicilioService extends BaseService<Domicilio, Long> {

    public DomicilioService(DomicilioRepository repository) {
        super(repository);
    }

    protected void validar(Domicilio domicilio) throws ErrorServiceException {
        try {
            if (domicilio.getCalle().isEmpty() || domicilio.getCalle() == null) {
                throw new ErrorServiceException("Debe indicar la dirección del domicilio");
            }
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }
}