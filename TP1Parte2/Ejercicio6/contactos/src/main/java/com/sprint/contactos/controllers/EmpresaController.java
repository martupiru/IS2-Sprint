package com.sprint.contactos.controllers;

import com.sprint.contactos.entities.Empresa;
import com.sprint.contactos.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/empresa")
public class EmpresaController extends BaseController<Empresa, String> {

    @Autowired
    private EmpresaService empresaService;

    public EmpresaController(EmpresaService service) {
        super(service);
        Empresa e = new Empresa();
        e.setId(null); // 🔹 evita el ID autogenerado al iniciar el form
        initController(
                e,
                "Listado de Empresas",
                "Empresa"
        );
    }

    @Override
    protected void preAlta() {
        // Hook: ejecutar algo antes de abrir formulario de alta (si querés)
    }

    @Override
    protected void preModificacion() {
        // Hook: acciones antes de editar (p. ej., precargar combos)
    }
}
