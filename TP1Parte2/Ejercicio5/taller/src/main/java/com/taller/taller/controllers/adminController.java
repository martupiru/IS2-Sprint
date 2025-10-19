package com.taller.taller.controllers;


import com.taller.taller.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sound.midi.ShortMessage;

@Controller
@RequestMapping("/admin")
public class adminController {

    @GetMapping("/panel")
    public String panelAdminController() {
        return "adminPanel";
    }

}
