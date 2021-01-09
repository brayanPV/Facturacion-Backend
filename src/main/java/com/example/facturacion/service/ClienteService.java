package com.example.facturacion.service;

import java.util.List;

import com.example.facturacion.entities.Cliente;

public interface ClienteService {

	public List<Cliente> findAll();
	public Cliente save(Cliente cliente);
	public Cliente findById(Long id);
	public void delete(Long id);
}
