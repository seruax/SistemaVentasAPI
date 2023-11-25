package com.seruax.SistemaVentasAPI.controller;

import com.seruax.SistemaVentasAPI.exception.NoEncontradoException;
import com.seruax.SistemaVentasAPI.model.Producto;
import com.seruax.SistemaVentasAPI.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> eliminarProducto(@PathVariable Long codigo_producto){
        try {
            productoServ.eliminarProducto(codigo_producto);
            return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al eliminar el producto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<?> editarProducto(@RequestBody Producto producto){
        try {
            productoServ.editarProducto(producto);
            return new ResponseEntity<>(productoServ.traerProducto(producto.getCodigo_producto()), HttpStatus.OK);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al editar el producto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Obtener lista de productos con cantidad_disponible inferior a X
    @GetMapping("/productos/falta_stock/{stockMinimo}")
    public List<Producto> faltaStock(@PathVariable int stockMinimo){
        return productoServ.faltaStock(stockMinimo);
    }


}
