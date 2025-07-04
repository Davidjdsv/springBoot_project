package com.ferreteriaSpringBoot.ferreteriaSpringBoot.controller;

import com.ferreteriaSpringBoot.ferreteriaSpringBoot.model.Clientes;
import com.ferreteriaSpringBoot.ferreteriaSpringBoot.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para gestionar las vistas y operaciones de clientes en la capa de presentación.
 * Proporciona endpoints para listar, crear, editar y eliminar clientes a través de una interfaz web.
 */
@Controller
public class VistaClientes {

    /**
     * Repositorio inyectado para acceder a las operaciones de base de datos de clientes.
     */
    @Autowired
    ClientesRepository clientesRepository;

    /**
     * Muestra la lista completa de clientes.
     *
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf que muestra la lista de clientes
     */
    @GetMapping("vista/clientes")
    public String getAll(Model model){
        model.addAttribute("clientes", clientesRepository.findAll());
        return "clientes";
    }

    /**
     * Muestra el formulario para crear un nuevo cliente.
     *
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf del formulario de cliente
     */
    @GetMapping("vistaC/form")
    public String form(Model model){
        model.addAttribute("clientes", new Clientes());
        return "clientes_form";
    }

    /**
     * Procesa el envío del formulario y guarda un nuevo cliente.
     *
     * @param clientes Objeto Cliente creado con los datos del formulario
     * @param ra Atributos para enviar mensajes flash después de redireccionar
     * @return Redirección a la vista principal de clientes
     */
    @PostMapping("vistaC/save")
    public String save(@ModelAttribute Clientes clientes, RedirectAttributes ra){
        clientesRepository.save(clientes);
        ra.addFlashAttribute("mensaje", "Cliente guardado");
        return "redirect:/vista/clientes";
    }

    /**
     * Muestra el formulario de edición para un cliente existente.
     *
     * @param id Identificador único del cliente a editar
     * @param model Objeto para pasar atributos a la vista
     * @return Nombre de la vista Thymeleaf del formulario de cliente
     */
    @GetMapping("vistaC/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Clientes clientes = clientesRepository.findById(id).orElse(null);
        model.addAttribute("clientes", clientes);
        return "clientes_form";
    }

    /**
     * Elimina un cliente existente.
     *
     * @param id Identificador único del cliente a eliminar
     * @param ra Atributos para enviar mensajes flash después de redireccionar
     * @return Redirección a la vista principal de clientes
     */
    @PostMapping("/vistaC/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra){
        clientesRepository.deleteById(id);
        ra.addFlashAttribute("mensaje", "Cliente eliminado con éxito");
        return "redirect:/vista/clientes";
    }
}