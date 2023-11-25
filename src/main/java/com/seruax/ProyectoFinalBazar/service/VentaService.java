package com.seruax.ProyectoFinalBazar.service;

import com.seruax.ProyectoFinalBazar.dto.VentaClienteDTO;
import com.seruax.ProyectoFinalBazar.exception.InsufficientStockException;
import com.seruax.ProyectoFinalBazar.exception.NoEncontradoException;
import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.model.Venta;
import com.seruax.ProyectoFinalBazar.repository.IVentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    // Sistema de Logging
    private static final Logger LOGGER = LoggerFactory.getLogger(VentaService.class);

    @Autowired
    private IVentaRepository ventaRepo;

    @Autowired
    private ProductoService productoServ;

    public void guardarVenta(Venta venta){
        List<Producto> listaProductos;
        listaProductos = venta.getListaProductos();
        double totalVenta= 0;
        // Comprueba si el producto existe, si hay suficiente stock, disminuye la cantidad y calcula el total de la venta
        for (Producto producto: listaProductos){
            if (productoServ.traerProducto(producto.getCodigo_producto()) != null) {
                Producto prod = productoServ.traerProducto(producto.getCodigo_producto());
                if (prod.getCantidad_disponible() >= 1) {
                    prod.setCantidad_disponible(prod.getCantidad_disponible() - 1);
                    productoServ.editarProducto(prod);
                    LOGGER.info("Producto {} actualizado correctamente. Nuevo stock: {}", prod.getNombre(), prod.getCantidad_disponible());
                    totalVenta += prod.getPrecio();
                } else {
                    LOGGER.error("No hay suficiente stock para el producto: {}", prod.getNombre());
                    throw new InsufficientStockException("No hay suficiente stock para el producto: " + prod.getNombre());
                }
            } else {
                LOGGER.error("No existe ningún producto con id: {}", producto.getCodigo_producto());
                throw new NoEncontradoException("No se encontró ningún producto con el ID: " + producto.getCodigo_producto() + ". " +
                        "\nSi deseas consultar la lista completa de productos, realiza una solicitud GET al endpoint \"/productos\".");
            }
        }
        venta.setTotal(totalVenta);
        ventaRepo.save(venta);
    }

    public List<Venta> traerVentas(){
        return ventaRepo.findAll();
    }

    public Venta traerVenta(Long codigo_venta){
        return ventaRepo.findById(codigo_venta).orElse(null);
    }

    public void eliminarVenta(Long codigo_venta){
        // Comprobamos que la venta existe
        if (traerVenta(codigo_venta) != null){
            // Eliminamos la venta
            ventaRepo.deleteById(codigo_venta);
            LOGGER.info("Venta con código {} eliminada correctamente", codigo_venta);
        } else {
            LOGGER.info("La venta con código {} no existe", codigo_venta);
            throw new NoEncontradoException("La venta con id " +  codigo_venta + " no existe");
        }
    }

    public void editarVenta(Long codigo_venta, LocalDate fecha_venta, Double total, List<Producto> listaProductos, Cliente unCliente){
        Venta venta = this.traerVenta(codigo_venta);

        venta.setFecha_venta(fecha_venta);
        venta.setTotal(total);
        venta.setListaProductos(listaProductos);
        venta.setUnCliente(unCliente);

        this.guardarVenta(venta);
    }

    public void editarVenta(Venta venta){
        // Comprobamos que la venta existe
        if (traerVenta(venta.getCodigo_venta()) != null){
            this.guardarVenta(venta);
        } else {
            LOGGER.info("La venta con código {} no existe", venta.getCodigo_venta());
            throw new NoEncontradoException("La venta con id " +  venta.getCodigo_venta() + " no existe");
        }
    }

    public List<Producto> mostrarProductos(Long codigo_venta){
        Venta venta = this.traerVenta(codigo_venta);
        return venta.getListaProductos();
    }

    public Map<String, Object> ventasDia(LocalDate dia){
        return ventaRepo.obtenerSumatoriaMontoYCantidadVentasPorFecha(dia);
    }

    public VentaClienteDTO mayorVenta(){
        Venta venta = ventaRepo.obtenerVentaConTotalMasAlto();

        VentaClienteDTO mayorVenta = new VentaClienteDTO();
        mayorVenta.setCodigoVenta(venta.getCodigo_venta());
        mayorVenta.setTotalVenta(venta.getTotal());
        mayorVenta.setCantidadProductos(venta.getListaProductos().size());
        mayorVenta.setNombreCliente(venta.getUnCliente().getNombre());
        mayorVenta.setApellidoCliente(venta.getUnCliente().getApellido());

        return mayorVenta;
    }
}
