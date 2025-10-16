package com.sprint.carrito.services;


import com.sprint.carrito.entities.Articulo;
import com.sprint.carrito.entities.Carrito;
import com.sprint.carrito.entities.Detalle;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.repositories.ArticuloRepository;
import com.sprint.carrito.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarritoService extends BaseService<Carrito,String>{
    protected CarritoService(CarritoRepository repository) {
        super(repository);
    }

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ArticuloRepository articuloRepository;

    protected void validar(Carrito carrito) throws ErrorServiceException {
        try {
            if (carrito.getTotal()<0) {
                throw new ErrorServiceException("Debe ingresar un valor positivo");
            }
        }catch(ErrorServiceException e) {
            throw e;
        }catch(Exception e) {
            throw new ErrorServiceException("Error de Sistemas");
        }
    }

    public Carrito agregarArticulo(String idCarrito, String idArticulo) throws ErrorServiceException {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new ErrorServiceException("No se encontró el carrito."));

        Articulo articulo = articuloRepository.findById(idArticulo)
                .orElseThrow(() -> new ErrorServiceException("No se encontró el artículo."));

        // Buscamos si el artículo ya está en el carrito
        Detalle detalleExistente = carrito.getDetalles().stream()
                .filter(d -> d.getArticulo().getId().equals(idArticulo))
                .findFirst()
                .orElse(null);

        if (detalleExistente != null) {
            // Si ya existe, incrementamos cantidad
            detalleExistente.setCantidad(detalleExistente.getCantidad() + 1);
        } else {
            // Si no existe, lo agregamos nuevo
            Detalle nuevoDetalle = new Detalle();
            nuevoDetalle.setArticulo(articulo);
            nuevoDetalle.setCarrito(carrito);
            nuevoDetalle.setCantidad(1);
            carrito.getDetalles().add(nuevoDetalle);
        }

        // Recalcular total del carrito
        double total = carrito.getDetalles().stream()
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();

        carrito.setTotal(total);

        return carritoRepository.save(carrito);
    }


    /**
     * Quita una unidad de un artículo del carrito. Busca y elimina una línea de Detalle.
     */
    public Carrito quitarArticulo(String idCarrito, String idArticulo) throws ErrorServiceException {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new ErrorServiceException("No se encontró el carrito."));

        Detalle detalle = carrito.getDetalles().stream()
                .filter(d -> d.getArticulo().getId().equals(idArticulo))
                .findFirst()
                .orElseThrow(() -> new ErrorServiceException("El artículo no se encuentra en el carrito."));

        // Reducimos cantidad o eliminamos
        if (detalle.getCantidad() > 1) {
            detalle.setCantidad(detalle.getCantidad() - 1);
        } else {
            carrito.getDetalles().remove(detalle);
        }

        // Recalculamos total
        double total = carrito.getDetalles().stream()
                .mapToDouble(d -> d.getArticulo().getPrecio() * d.getCantidad())
                .sum();
        carrito.setTotal(total);

        return carritoRepository.save(carrito);
    }


}
