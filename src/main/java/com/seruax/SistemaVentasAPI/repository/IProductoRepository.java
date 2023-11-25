package com.seruax.SistemaVentasAPI.repository;

import com.seruax.SistemaVentasAPI.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p WHERE p.cantidad_disponible < :stockMinimo")
    List<Producto> findByCantidadDisponibleMenorQue(@Param("stockMinimo") int stockMinimo);

}