package com.seruax.ProyectoFinalBazar.controller;

import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {

    @Autowired
    private ProductoService productoServ;

    @PostMapping("/productos/crear")
    public String guardarProducto(@RequestBody Producto producto){
        productoServ.guardarProducto(producto);
        return "Producto creado correctamente";
    }

    @GetMapping("/productos")
    public List<Producto> traerProductos(){
        return productoServ.traerProductos();
    }

    @GetMapping("/productos/{codigo_producto}")
    public Producto traerProducto(@PathVariable Long codigo_producto){
        return productoServ.traerProducto(codigo_producto);
    }

    @DeleteMapping("/productos/eliminar/{codigo_producto}")
    public String eliminarProducto(@PathVariable Long codigo_producto){
        productoServ.eliminarProducto(codigo_producto);
        return "Producto eliminado correctamente";
    }

    // Modificar producto mediante variables de ruta (PathVariable)
    @PutMapping("/productos/editar/{codigo_producto}")
    public Producto editarProducto(@PathVariable Long codigo_producto,
                                 @RequestParam(required = false, name = "nombre") String nombre,
                                 @RequestParam(required = false, name = "marca") String marca,
                                 @RequestParam(required = false, name = "costo") Double costo,
                                 @RequestParam(required = false, name = "cantidad_disponible") Double cantidad_disponible){
        productoServ.editarProducto(codigo_producto, nombre, marca, costo, cantidad_disponible);
        return productoServ.traerProducto(codigo_producto);
    }

    // Modificar producto mediante RequestBody
    @PutMapping("/productos/editar")
    public Producto editarProducto(@RequestBody Producto producto){
        productoServ.editarProducto(producto);
        return productoServ.traerProducto(producto.getCodigo_producto());
    }

    // Obtener lista de productos con cantidad_disponible inferior a X
    @GetMapping("/productos/falta_stock/{stockMinimo}")
    public List<Producto> faltaStock(@PathVariable int stockMinimo){
        return productoServ.faltaStock(stockMinimo);
    }


}
