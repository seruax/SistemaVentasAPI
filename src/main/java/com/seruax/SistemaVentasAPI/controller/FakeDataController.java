package com.seruax.SistemaVentasAPI.controller;

import com.seruax.SistemaVentasAPI.service.FakeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeDataController {

    @Autowired
    private FakeDataService fakeDataServ;

    @PostMapping("/fakeData/producto/{numeroDeRegistros}")
    public String generarFakeDataProducto(@PathVariable int numeroDeRegistros){
        fakeDataServ.generarFakeDataProducto(numeroDeRegistros);
        return "Se ha creado " + numeroDeRegistros + " nuevos registros fake de Producto";
    }

    @PostMapping("/fakeData/cliente/{numeroDeRegistros}")
    public String generarFakeDataCliente(@PathVariable int numeroDeRegistros){
        fakeDataServ.generarFakeDataCliente(numeroDeRegistros);
        return "Se ha creado " + numeroDeRegistros + " nuevos registros fake de Cliente";
    }

}
