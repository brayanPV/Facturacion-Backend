package com.example.facturacion.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.facturacion.entities.Cliente;
import com.example.facturacion.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteImpl;
	
	@GetMapping("/")
	public List<Cliente> index() {
		return clienteImpl.findAll();
	}
}
