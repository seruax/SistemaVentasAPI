package com.seruax.ProyectoFinalBazar.service;

import com.seruax.ProyectoFinalBazar.dto.VentaClienteDTO;
import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.model.Venta;
import com.seruax.ProyectoFinalBazar.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private IVentaRepository ventaRepo;

    public void guardarVenta(Venta venta){
        ventaRepo.save(venta);
    }

    public List<Venta> traerVentas(){
        return ventaRepo.findAll();
    }

    public Venta traerVenta(Long codigo_venta){
        return ventaRepo.findById(codigo_venta).orElse(null);
    }

    public void eliminarVenta(Long codigo_venta){
        ventaRepo.deleteById(codigo_venta);
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
        this.guardarVenta(venta);
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
