
package com.is.biblioteca.controller.view;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.is.biblioteca.business.domain.entity.Usuario;
import com.is.biblioteca.business.logic.error.ErrorServiceException;
import com.is.biblioteca.business.logic.service.UsuarioService;

@Controller
@RequestMapping("/admin")
public class AdminstracionController {

	@Autowired
	private UsuarioService usuarioService;

	//////////////////////////////////////////
	//////////////////////////////////////////
	///////// VIEW: PANEL ADMINISTRADOR ////// 
	//////////////////////////////////////////
	//////////////////////////////////////////

    @GetMapping("/dashboard")
    public String irPanelAdministrativo() {
        return "panel.html";
    }

    //////////////////////////////////////////
    //////////////////////////////////////////
    ////////////// VIEW: LOGIN ///////////////
    //////////////////////////////////////////
    //////////////////////////////////////////

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {

        try {

            List<Usuario> usuarios = usuarioService.listarUsuario();
            modelo.addAttribute("usuarios", usuarios);

            return "usuario_list";

        } catch (Exception e) {
            return null;
        }
    }

    //////////////////////////////////////////
    //////////////////////////////////////////
    ///////// VIEW: MODIFICAR ROL ////////////
    //////////////////////////////////////////
    //////////////////////////////////////////

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(ModelMap modelo, @PathVariable String id) {

        try {

            usuarioService.cambiarRol(id);

        } catch (ErrorServiceException ex) {
            modelo.put("error", ex.getMessage());
        }

        return "redirect:/admin/usuarios";
    }
}
