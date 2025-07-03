package com.ferreteriaSpringBoot.ferreteriaSpringBoot.controller;

import com.ferreteriaSpringBoot.ferreteriaSpringBoot.model.Clientes;
import com.ferreteriaSpringBoot.ferreteriaSpringBoot.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestionar operaciones CRUD de clientes.
 * Proporciona endpoints para crear, leer, actualizar y eliminar registros de clientes.
 */
@RestController //Devuelve datos tipo json
@RequestMapping("/api/clientes")
public class ControllerClientes {

    /**
     * Repositorio inyectado para acceder a las operaciones de base de datos de clientes.
     */
    @Autowired
    private ClientesRepository clientesRepository;

    /**
     * Obtiene todos los clientes registrados en el sistema.
     *
     * @return Lista completa de clientes en formato JSON.
     */
    @GetMapping
    public List<Clientes> obtenerTodosClientes() {
        return clientesRepository.findAll();
    }

    /**
     * Busca un cliente específico por su identificador único.
     *
     * @param id Identificador único del cliente a buscar.
     * @return El cliente encontrado o null si no existe.
     */
    @GetMapping("/{id}")
    public Clientes obtenerClientePorId(@PathVariable Long id) {
        return clientesRepository.findById(id).orElse(null);
    }

    /**
     * Crea un nuevo registro de cliente en el sistema.
     *
     * @param cliente Datos del cliente a crear en el cuerpo de la solicitud.
     * @return El cliente creado con su identificador generado.
     */
    @PostMapping
    public Clientes crearCliente(@RequestBody Clientes cliente) {
        return clientesRepository.save(cliente);
    }

    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param id Identificador único del cliente a actualizar.
     * @param clienteDetalles Nuevos datos del cliente en el cuerpo de la solicitud.
     * @return El cliente actualizado.
     */
    @PutMapping("/{id}")
    public Clientes actualizarCliente(@PathVariable Long id, @RequestBody Clientes clienteDetalles) {
        clienteDetalles.setId_cliente(id);
        return clientesRepository.save(clienteDetalles);
    }

    /**
     * Elimina un cliente del sistema por su identificador único.
     *
     * @param id Identificador único del cliente a eliminar.
     */
    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        clientesRepository.deleteById(id);
    }
}