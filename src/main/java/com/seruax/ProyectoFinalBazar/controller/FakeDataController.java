package com.seruax.ProyectoFinalBazar.controller;

import com.seruax.ProyectoFinalBazar.service.FakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeDataController {

    @Autowired
    private FakeDataService fakeDataServ;

    @GetMapping("/fakeData/producto/{numeroDeRegistros}")
    public String generarFakeDataProducto(@PathVariable int numeroDeRegistros){
        fakeDataServ.generarFakeDataProducto(numeroDeRegistros);
        return "Se ha creado " + numeroDeRegistros + " nuevos registros fake de Producto";
    }

    @GetMapping("/fakeData/cliente/{numeroDeRegistros}")
    public String generarFakeDataCliente(@PathVariable int numeroDeRegistros){
        fakeDataServ.generarFakeDataCliente(numeroDeRegistros);
        return "Se ha creado " + numeroDeRegistros + " nuevos registros fake de Cliente";
    }

}
