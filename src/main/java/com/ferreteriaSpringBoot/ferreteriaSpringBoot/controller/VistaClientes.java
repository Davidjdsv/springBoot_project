package com.ferreteriaSpringBoot.ferreteriaSpringBoot.controller;

import com.ferreteriaSpringBoot.ferreteriaSpringBoot.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaClientes {
    @Autowired
    ClientesRepository clientesRepository;

    @GetMapping("vista/clientes")
    public String getAll(Model model){
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes";
    }



}
