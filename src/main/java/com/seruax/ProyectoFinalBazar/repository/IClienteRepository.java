package com.seruax.ProyectoFinalBazar.repository;

import com.seruax.ProyectoFinalBazar.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
}
