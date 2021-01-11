package com.example.facturacion.dao;

import com.example.facturacion.entities.Producto;

import org.springframework.data.repository.CrudRepository;

public interface ProductoDAO extends CrudRepository<Producto, Long>{
    
}
