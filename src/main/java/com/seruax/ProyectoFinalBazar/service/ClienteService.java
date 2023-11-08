package com.seruax.ProyectoFinalBazar.service;

import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepo;

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
        this.guardarCliente(cliente);
    }

}
