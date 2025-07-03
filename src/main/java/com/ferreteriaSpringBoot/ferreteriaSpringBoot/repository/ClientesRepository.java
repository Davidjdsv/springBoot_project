package com.ferreteriaSpringBoot.ferreteriaSpringBoot.repository;

import com.ferreteriaSpringBoot.ferreteriaSpringBoot.model.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
