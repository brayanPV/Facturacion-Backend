package com.example.facturacion.dao;

import org.springframework.data.repository.CrudRepository;

import com.example.facturacion.entities.Cliente;

public interface ClienteDAO extends CrudRepository<Cliente, Long>{

}
