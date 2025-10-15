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

        // Creamos una nueva instancia de Detalle
        Detalle nuevoDetalle = new Detalle();
        nuevoDetalle.setArticulo(articulo);
        nuevoDetalle.setCarrito(carrito); // Asignamos la relación

        // Agregamos el nuevo detalle a la lista del carrito
        carrito.getDetalles().add(nuevoDetalle);

        // Recalculamos el total del carrito
        carrito.setTotal(carrito.getTotal() + articulo.getPrecio());

        // Al guardar el carrito, la cascada guardará el nuevo detalle.
        return carritoRepository.save(carrito);
    }

    /**
     * Quita una unidad de un artículo del carrito. Busca y elimina una línea de Detalle.
     */
    public Carrito quitarArticulo(String idCarrito, String idArticulo) throws ErrorServiceException {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new ErrorServiceException("No se encontró el carrito."));

        //Creamos una variable para guardar el detalle que encontremos. La inicializamos en null.
        Detalle detalleParaEliminar = null;

        //Recorremos la lista de detalles del carrito, uno por uno.
        for (Detalle detalleActual : carrito.getDetalles()) {

            //Para cada detalle, comprobamos si el ID de su artículo es el que buscamos.
            if (detalleActual.getArticulo().getId().equals(idArticulo)) {

                //Si lo encontramos o guardamos en nuestra variable.
                detalleParaEliminar = detalleActual;

                //Como solo necesitabamos el primero, salimos del bucle para no seguir buscando.
                break;
            }
        }

        //Después de recorrer la lista, comprobamos si lo encontramos.
        if (detalleParaEliminar == null) {
            // Si la variable sigue en null, significa que el artículo no estaba en el carrito.
            throw new ErrorServiceException("El artículo no se encuentra en el carrito.");
        }

        carrito.getDetalles().remove(detalleParaEliminar);
        carrito.setTotal(carrito.getTotal() - detalleParaEliminar.getArticulo().getPrecio());

        return carritoRepository.save(carrito);
    }

}
