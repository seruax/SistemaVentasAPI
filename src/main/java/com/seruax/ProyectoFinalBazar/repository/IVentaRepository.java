package com.seruax.ProyectoFinalBazar.repository;

import com.seruax.ProyectoFinalBazar.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {

    // Esta es con Query Native
    @Query("SELECT SUM(v.total) AS totalMonto, COUNT(v) AS cantidadVentas FROM Venta v WHERE v.fecha_venta = :fecha")
    Map<String, Object> obtenerSumatoriaMontoYCantidadVentasPorFecha(@Param("fecha") LocalDate fecha);

    // Esto es con JPQL
//    @Query(value = "SELECT SUM(total) AS totalMonto, COUNT(*) AS cantidadVentas FROM Venta WHERE fecha_venta = :fecha", nativeQuery = true)
//    Map<String, Object> obtenerSumatoriaMontoYCantidadVentasPorFecha(@Param("fecha") LocalDate fecha);

    @Query(value = "SELECT * FROM Venta ORDER BY total DESC LIMIT 1", nativeQuery = true)
    Venta obtenerVentaConTotalMasAlto();
}