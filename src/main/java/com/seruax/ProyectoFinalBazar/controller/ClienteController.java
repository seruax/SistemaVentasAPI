package com.seruax.ProyectoFinalBazar.controller;

import com.seruax.ProyectoFinalBazar.exception.NoEncontradoException;
import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id_cliente){
        try {
            clienteServ.eliminarCliente(id_cliente);
            return new ResponseEntity<>("Cliente eliminado correctamente", HttpStatus.OK);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al eliminar el cliente: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Modificar cliente mediante variables de ruta (PathVariable)
    @PutMapping("/clientes/editar/{id_cliente}")
    public Cliente editarCliente(@PathVariable Long id_cliente,
                                   @RequestParam(required = false, name = "nombre") String nombre,
                                   @RequestParam(required = false, name = "apellido") String apellido,
                                   @RequestParam(required = false, name = "dni") String dni){
        clienteServ.editarCliente(id_cliente, nombre, apellido, dni);
        return clienteServ.traerCliente(id_cliente);
    }

    // Modificar cliente mediante RequestBody
    @PutMapping("/clientes/editar")
    public ResponseEntity<?> editarCliente(@RequestBody Cliente cliente){
        try {
            clienteServ.editarCliente(cliente);
            return new ResponseEntity<>(clienteServ.traerCliente(cliente.getId_cliente()), HttpStatus.OK);
        } catch (NoEncontradoException e) {
            return new ResponseEntity<>("Error al editar el cliente: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
