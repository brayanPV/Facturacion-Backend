package com.example.facturacion.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.facturacion.entities.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Long>{

    @Query("select c from Cliente c left join fetch c.facturas f where c.id =?1")
    public Cliente fetchByIdWithFacturas(Long id);
}
