package com.seruax.SistemaVentasAPI.controller;

import com.seruax.SistemaVentasAPI.dto.VentaClienteDTO;
import com.seruax.SistemaVentasAPI.exception.InsufficientStockException;
import com.seruax.SistemaVentasAPI.exception.NoEncontradoException;
import com.seruax.SistemaVentasAPI.model.Cliente;
import com.seruax.SistemaVentasAPI.model.Producto;
import com.seruax.SistemaVentasAPI.model.Venta;
import com.seruax.SistemaVentasAPI.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class VentaController {

    @Autowired
    private VentaService ventaServ;

    @PostMapping("/ventas/crear")
    public ResponseEntity<String> guardarVenta(@RequestBody Venta venta){
        try {
            ventaServ.guardarVenta(venta);
            return new ResponseEntity<>("Venta creada correctamente", HttpStatus.OK);
        } catch (InsufficientStockException e) {
            return new ResponseEntity<>("Error al crear la venta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al crear la venta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ventas")
    public List<Venta> traerVentas(){
        return ventaServ.traerVentas();
    }

    @GetMapping("/ventas/{codigo_venta}")
    public Venta traerVenta(@PathVariable Long codigo_venta){
        return ventaServ.traerVenta(codigo_venta);
    }

    @DeleteMapping("/ventas/eliminar/{codigo_venta}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long codigo_venta){
        try {
            ventaServ.eliminarVenta(codigo_venta);
            return new ResponseEntity<>("Venta eliminada correctamente", HttpStatus.OK);
        } catch (NoEncontradoException e){
            return new ResponseEntity<>("Error al eliminar la venta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Modificar venta mediante variables de ruta (PathVariable)
    @PutMapping("/ventas/editar/{codigo_venta}")
    public Venta editarVenta(@PathVariable Long codigo_venta,
                                   @RequestParam(required = false, name = "fecha_venta") LocalDate fecha_venta,
                                   @RequestParam(required = false, name = "total") Double total,
                                   @RequestParam(required = false, name = "listaProductos") List<Producto> listaProductos,
                                   @RequestParam(required = false, name = "unCliente") Cliente unCliente){
        ventaServ.editarVenta(codigo_venta, fecha_venta, total, listaProductos, unCliente);
        return ventaServ.traerVenta(codigo_venta);
    }

    // Modificar venta mediante RequestBody
    @PutMapping("/ventas/editar")
    public ResponseEntity<?> editarVenta(@RequestBody Venta venta){
        try {
            ventaServ.editarVenta(venta);
            return new ResponseEntity<>(ventaServ.traerVenta(venta.getCodigo_venta()), HttpStatus.OK);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al editar la venta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener la lista de productos de una determinada venta
    @GetMapping("/ventas/productos/{codigo_venta}")
    public List<Producto> mostrarProductos(@PathVariable Long codigo_venta){
        return ventaServ.mostrarProductos(codigo_venta);
    }

    // Obtener la sumatoria del monto y también cantidad total de ventas de un determinado dia
    @GetMapping("/ventas/dia/{fecha_venta}")
    public Map<String, Object> ventasDia(@PathVariable String fecha_venta){
        LocalDate fecha = LocalDate.parse(fecha_venta);
        return ventaServ.ventasDia( fecha);
    }

    // otra manera de hacerlo
//    @GetMapping("/ventas/dia/{fecha_venta}")
//    public Map<String, Object> ventasDia(@PathVariable("fecha_venta") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha_venta) {
//        return ventaServ.ventasDia(fecha_venta);
//    }

    // Obtener el codigo_venta, el total, la cantidad de productos, el nombre del cliente y el
    // apellido del cliente de la venta con el monto más alto de todas.
    @GetMapping("/ventas/mayor_venta")
    public VentaClienteDTO mayorVenta(){
        return ventaServ.mayorVenta();
    }

}