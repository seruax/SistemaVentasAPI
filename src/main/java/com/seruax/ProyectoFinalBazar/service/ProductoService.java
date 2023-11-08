package com.seruax.ProyectoFinalBazar.service;

import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private IProductoRepository productoRepo;

    public void guardarProducto(Producto producto){
        productoRepo.save(producto);
    }

    public List<Producto> traerProductos(){
        return productoRepo.findAll();
    }

    public Producto traerProducto(Long codigo_producto){
        return productoRepo.findById(codigo_producto).orElse(null);
    }

    public void eliminarProducto(Long codigo_producto){
        productoRepo.deleteById(codigo_producto);
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
        this.guardarProducto(producto);
    }

    public List<Producto> faltaStock(int stockMinimo){
        return productoRepo.findByCantidadDisponibleMenorQue(stockMinimo);
    }
}
