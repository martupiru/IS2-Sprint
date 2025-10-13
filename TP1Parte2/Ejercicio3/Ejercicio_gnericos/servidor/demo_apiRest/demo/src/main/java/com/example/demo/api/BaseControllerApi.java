package com.example.demo.api;



import com.example.demo.entities.BaseEntity;
import com.example.demo.services.BaseService;
import com.example.demo.services.ErrorServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseControllerApi<T extends BaseEntity<ID>, ID> {

    protected final BaseService<T, ID> service;

    protected BaseControllerApi(BaseService<T, ID> service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(@RequestParam Map<String, String> params) {
        try {
            List<T> activos = service.listarActivos();

            // Si hay parámetros, aplicar filtros genéricos
            if (!params.isEmpty()) {
                activos = filtrarPorParametros(activos, params);
            }

            return ResponseEntity.status(HttpStatus.OK).body(activos);
        } catch (ErrorServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error de Sistema"));
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable ID id) {
        try {
            T entidad = service.getOne(id);
            return ResponseEntity.status(HttpStatus.OK).body(entidad);
        } catch (ErrorServiceException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Entidad no encontrada"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody T entidad) {
        try {
            preCreate(entidad);
            T guardado = service.alta(entidad);
            postCreate(guardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (ErrorServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al crear la entidad"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T entidad) {
        try {
            preUpdate(entidad);
            Optional<T> actualizado = service.modificar(id, entidad);

            if (actualizado.isPresent()) {
                postUpdate(actualizado.get());
                return ResponseEntity.status(HttpStatus.OK).body(actualizado.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Entidad no encontrada"));
            }
        } catch (ErrorServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error al actualizar la entidad"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        try {
            preDelete(id);
            boolean eliminado = service.bajaLogica(id);

            if (eliminado) {
                postDelete(id);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new SuccessResponse("Entidad eliminada correctamente"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Entidad no encontrada"));
            }
        } catch (ErrorServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error al eliminar la entidad"));
        }
    }

    // Métodospara ser sobrescritos en las clases hijas si es necesario
    protected void preCreate(T entidad) throws ErrorServiceException {}
    protected void postCreate(T entidad) throws ErrorServiceException {}
    protected void preUpdate(T entidad) throws ErrorServiceException {}
    protected void postUpdate(T entidad) throws ErrorServiceException {}
    protected void preDelete(ID id) throws ErrorServiceException {}
    protected void postDelete(ID id) throws ErrorServiceException {}


    /**
     * Filtra una lista de entidades usando reflexión para acceder a los campos dinámicamente
     * Metodo protegido para que las subclases puedan sobrescribirlo si necesitan lógica personalizada
     */
    protected List<T> filtrarPorParametros(List<T> lista, Map<String, String> params) {
        // basicamente busca con el paramatro el getter correspondiente y con el valor compara si contiene el valor buscado
        return lista.stream().filter(entidad -> {
            return params.entrySet().stream().allMatch(entry -> {
                try {
                    String nombreCampo = entry.getKey();
                    String valorBuscado = entry.getValue().toLowerCase();

                    // Obtener el getter del campo usando reflexión
                    String getterName = "get" + nombreCampo.substring(0, 1).toUpperCase() + nombreCampo.substring(1);
                    java.lang.reflect.Method getter = entidad.getClass().getMethod(getterName);
                    Object valorCampo = getter.invoke(entidad);

                    // Comparar valores
                    if (valorCampo == null) {
                        return false;
                    }

                    return valorCampo.toString().equalsIgnoreCase(valorBuscado);

                } catch (Exception e) {
                    // Si el campo no existe o hay error, ignorar este filtro
                    return true;
                }
            });
        }).collect(java.util.stream.Collectors.toList());
    }


    // Clases internas para respuestas estandarizadas
    protected static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    protected static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}