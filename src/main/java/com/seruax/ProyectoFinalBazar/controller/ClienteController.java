package com.seruax.ProyectoFinalBazar.controller;

import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteServ;

    @PostMapping("/clientes/crear")
    public String guardarCliente(@RequestBody Cliente cliente){
        clienteServ.guardarCliente(cliente);
        return "Cliente creado correctamente";
    }

    @GetMapping("/clientes")
    public List<Cliente> traerClientes(){
        return clienteServ.traerClientes();
    }

    @GetMapping("/clientes/{id_cliente}")
    public Cliente traerCliente(@PathVariable Long id_cliente){
        return clienteServ.traerCliente(id_cliente);
    }

    @DeleteMapping("/clientes/eliminar/{id_cliente}")
    public String eliminarCliente(@PathVariable Long id_cliente){
        clienteServ.eliminarCliente(id_cliente);
        return "Cliente eliminado correctamente";
    }

    @PutMapping("/clientes/editar/{id_cliente}")
    public Cliente editarCliente(@PathVariable Long id_cliente,
                                   @RequestParam(required = false, name = "nombre") String nombre,
                                   @RequestParam(required = false, name = "apellido") String apellido,
                                   @RequestParam(required = false, name = "dni") String dni){
        clienteServ.editarCliente(id_cliente, nombre, apellido, dni);
        return clienteServ.traerCliente(id_cliente);
    }

    @PutMapping("/clientes/editar")
    public Cliente editarCliente(Cliente cliente){
        clienteServ.editarCliente(cliente);
        return clienteServ.traerCliente(cliente.getId_cliente());
    }

}
