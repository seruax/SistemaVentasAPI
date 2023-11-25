package com.seruax.ProyectoFinalBazar.service;

import com.seruax.ProyectoFinalBazar.exception.NoEncontradoException;
import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.model.Venta;
import com.seruax.ProyectoFinalBazar.repository.IProductoRepository;
import com.seruax.ProyectoFinalBazar.repository.IVentaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    // Sistema de Logging
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private IProductoRepository productoRepo;

    @Autowired
    private IVentaRepository ventaRepo;

    public void guardarProducto(Producto producto){
        productoRepo.save(producto);
    }

    public List<Producto> traerProductos(){
        return productoRepo.findAll();
    }

    public Producto traerProducto(Long codigo_producto){
        return productoRepo.findById(codigo_producto).orElse(null);
    }

    @Transactional
    public void eliminarProducto(Long codigo_producto){
        //Comprobamos que el producto exista
        Producto productoAEliminar = traerProducto(codigo_producto);
        if (productoAEliminar != null){
            // Si existe, eliminamos las filas en venta_producto que hacen referencia a este producto
            // Buscamos las ventas que contienen el producto
            List<Venta> ventasConProducto = ventaRepo.findVentasByCodigoProducto(codigo_producto);
            // Eliminamos el producto de cada venta
            for (Venta venta : ventasConProducto) {
                venta.getListaProductos().removeIf(producto -> producto.getCodigo_producto().equals(codigo_producto));
                ventaRepo.save(venta);
            }
            // Una vez eliminadas todas las referencias, eliminamos el producto
            productoRepo.deleteById(codigo_producto);
            LOGGER.info("Producto {} eliminado correctamente", productoAEliminar.getNombre());
        } else {
            LOGGER.error("El producto con código {} no existe", codigo_producto);
            throw new NoEncontradoException("El producto con código " + codigo_producto + " no existe");
        }
    }

    public void editarProducto(Long codigo_producto, String nombre, String marca, Double precio, Double cantidad_disponible){
        Producto producto = this.traerProducto(codigo_producto);

        producto.setNombre(nombre);
        producto.setMarca(marca);
        producto.setPrecio(precio);
        producto.setPrecio(precio);
        producto.setCantidad_disponible(cantidad_disponible);

        this.guardarProducto(producto);
    }

    public void editarProducto(Producto producto){
        // Comprobamos que el producto existe
        if (traerProducto(producto.getCodigo_producto()) != null){
            this.guardarProducto(producto);
        } else {
            LOGGER.info("El producto con código {} no existe", producto.getCodigo_producto());
            throw new NoEncontradoException("El producto con id " + producto.getCodigo_producto() + " no existe");
        }
    }

    public List<Producto> faltaStock(int stockMinimo){
        return productoRepo.findByCantidadDisponibleMenorQue(stockMinimo);
    }
}
