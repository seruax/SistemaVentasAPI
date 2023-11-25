package com.seruax.ProyectoFinalBazar.service;

import com.github.javafaker.Faker;
import com.seruax.ProyectoFinalBazar.model.Cliente;
import com.seruax.ProyectoFinalBazar.model.Producto;
import com.seruax.ProyectoFinalBazar.repository.IClienteRepository;
import com.seruax.ProyectoFinalBazar.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakeDataService {

    @Autowired
    private IProductoRepository productoRepo;

    @Autowired
    private IClienteRepository clienteRepo;

    // Fake data para Producto
    public void generarFakeDataProducto(int numeroDeRegistros){
        Faker faker = new Faker();
        for (int i = 0; i < numeroDeRegistros; i++){
            Producto producto = new Producto();
            producto.setNombre(faker.food().ingredient());
            producto.setMarca(faker.company().name());
            producto.setPrecio(faker.number().randomDouble(2, 1, 100));
            producto.setCantidad_disponible(faker.number().randomDouble(0, 1, 100));
            productoRepo.save(producto);
        }
    }

    // Fake data para cliente
    public void generarFakeDataCliente(int numeroDeRegistros){
        Faker faker = new Faker();
        for (int i = 0; i < numeroDeRegistros; i++){
            Cliente cliente = new Cliente();
            cliente.setNombre(faker.name().firstName());
            cliente.setApellido(faker.name().lastName());
            cliente.setDni(faker.idNumber().valid());
            clienteRepo.save(cliente);
        }
    }

    // Las ventas deben crearse manualmente con los datos de cliente y productos existentes

}
