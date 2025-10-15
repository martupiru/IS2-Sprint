package com.sprint.carrito.controllers;

import com.sprint.carrito.entities.Articulo;
import com.sprint.carrito.entities.Proveedor;
import com.sprint.carrito.error.ErrorServiceException;
import com.sprint.carrito.services.ArticuloService;
import com.sprint.carrito.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articulo")
public class ArticuloController extends BaseController<Articulo, String> {


    private final ArticuloService articuloService;
    private final ProveedorService proveedorService;

    @Autowired
    public ArticuloController(ArticuloService articuloService, ProveedorService proveedorService) {
        super(articuloService);

        this.articuloService = articuloService;
        this.proveedorService = proveedorService;

        initController(new Articulo(), "Gestión de Artículos", "Editar Artículo");
    }

    @Override
    protected void preAlta() throws ErrorServiceException {
        try {
            List<Proveedor> proveedores = proveedorService.listarActivos();
            this.model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar los proveedores.");
        }
    }

    @Override
    protected void preModificacion() throws ErrorServiceException {
        try {
            List<Proveedor> proveedores = proveedorService.listarActivos();
            this.model.addAttribute("proveedores", proveedores);
        } catch (Exception e) {
            throw new ErrorServiceException("No se pudieron cargar los proveedores.");
        }
    }

    @GetMapping("/buy")
    public String mostrarPaginaDeVenta(Model model) {
        try {
            List<Articulo> listaDeArticulos = articuloService.listarActivos();
            model.addAttribute("articulos", listaDeArticulos);
        } catch (Exception e) {
            model.addAttribute("error", "No se pudieron cargar los productos.");
        }
        return "buy";
    }
}