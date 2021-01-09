package com.example.facturacion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.facturacion.dao.ClienteDAO;
import com.example.facturacion.entities.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteDAO clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>)clienteDao.findAll();
	}

	@Override
	public Cliente save(Cliente cliente) {
		return clienteDao.save(cliente);
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

}
