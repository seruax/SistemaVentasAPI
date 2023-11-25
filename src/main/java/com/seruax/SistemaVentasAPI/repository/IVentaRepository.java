package com.seruax.SistemaVentasAPI.repository;

import com.seruax.SistemaVentasAPI.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {

    // Esta consulta es con JPQL
    @Query("SELECT SUM(v.total) AS totalMonto, COUNT(v) AS cantidadVentas FROM Venta v WHERE v.fecha_venta = :fecha")
    Map<String, Object> obtenerSumatoriaMontoYCantidadVentasPorFecha(@Param("fecha") LocalDate fecha);

    // Esta es con Query Native
//    @Query(value = "SELECT SUM(total) AS totalMonto, COUNT(*) AS cantidadVentas FROM Venta WHERE fecha_venta = :fecha", nativeQuery = true)
//    Map<String, Object> obtenerSumatoriaMontoYCantidadVentasPorFecha(@Param("fecha") LocalDate fecha);

    @Query(value = "SELECT * FROM Venta ORDER BY total DESC LIMIT 1", nativeQuery = true)
    Venta obtenerVentaConTotalMasAlto();

    @Query("SELECT v FROM Venta v JOIN v.listaProductos p WHERE p.codigo_producto = :codigo_producto")
    List<Venta> findVentasByCodigoProducto(@Param("codigo_producto") Long codigo_producto);

}
