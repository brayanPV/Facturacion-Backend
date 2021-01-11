package com.example.facturacion.service;

import java.util.List;

import com.example.facturacion.entities.Factura;

public interface FacturaService {
    public List<Factura> findAll();
	public Factura save(Factura factura);
	public Factura findById(Long id);
	public void delete(Long id);
}
