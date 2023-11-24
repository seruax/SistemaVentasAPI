package com.seruax.ProyectoFinalBazar.repository;

import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT v FROM Venta v WHERE v.unCliente = :clienteAEliminar")
    List<Venta> findVentasByCliente(@Param("clienteAEliminar") Cliente clienteAEliminar);

}
