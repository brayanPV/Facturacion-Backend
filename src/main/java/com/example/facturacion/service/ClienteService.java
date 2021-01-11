package com.example.facturacion.service;

import java.util.List;

import com.example.facturacion.entities.Cliente;
import com.example.facturacion.entities.Factura;
import com.example.facturacion.entities.Producto;

public interface ClienteService {

	public List<Cliente> findAll();
	public Cliente save(Cliente cliente);
	public Cliente findById(Long id);
	public void delete(Long id);
	public Cliente fetchByIdWithFacturas(Long id);
	public Producto findProductoById(Long id);
	public Factura saveFactura(Factura factura);
	public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);
}
