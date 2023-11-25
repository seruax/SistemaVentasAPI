package com.seruax.SistemaVentasAPI.service;

import com.seruax.SistemaVentasAPI.exception.NoEncontradoException;
import com.seruax.SistemaVentasAPI.model.Cliente;
import com.seruax.SistemaVentasAPI.model.Venta;
import com.seruax.SistemaVentasAPI.repository.IClienteRepository;
import com.seruax.SistemaVentasAPI.repository.IVentaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    // Sistema de Logging
    private static final Logger LOGGER = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private IClienteRepository clienteRepo;

    @Autowired
    private IVentaRepository ventaRepo;

    public void guardarCliente(Cliente cliente){
        clienteRepo.save(cliente);
    }

    public List<Cliente> traerClientes(){
        return clienteRepo.findAll();
    }

    public Cliente traerCliente(Long id_cliente){
        return clienteRepo.findById(id_cliente).orElse(null);
    }

    public void eliminarCliente(Long id_cliente){
        // Comprobamos que el cliente existe
        Cliente clienteAEliminar = traerCliente(id_cliente);
        // Si existe, eliminamos sus ventas
        if (clienteAEliminar != null){
            List<Venta> ventasConCliente = clienteRepo.findVentasByCliente(clienteAEliminar);
            for (Venta venta : ventasConCliente) {
                ventaRepo.delete(venta);
            }
            // Una vez eliminadas las ventas del cliente, eliminamos el cliente
            clienteRepo.deleteById(id_cliente);
            LOGGER.info("Cliente {} eliminado correctamente", clienteAEliminar.getNombre());
        } else {
            LOGGER.error("El cliente con id {} no existe", id_cliente);
            throw new NoEncontradoException("El cliente con id " + id_cliente + " no existe");
        }

        clienteRepo.deleteById(id_cliente);
    }

    public void editarCliente(Long id_cliente, String nombre, String apellido, String dni){
        Cliente cliente = this.traerCliente(id_cliente);

        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);

        this.guardarCliente(cliente);
    }

    public void editarCliente(Cliente cliente){
        // Comprobamos que el cliente existe
        if (traerCliente(cliente.getId_cliente()) != null){
            this.guardarCliente(cliente);
        } else {
            LOGGER.info("El cliente con ID {} no existe", cliente.getId_cliente());
            throw new NoEncontradoException("El cliente con id " + cliente.getId_cliente() + " no existe");
        }
    }

}
