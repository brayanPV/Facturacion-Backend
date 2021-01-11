package com.example.facturacion.service;

import java.util.List;

import com.example.facturacion.dao.FacturaDAO;
import com.example.facturacion.entities.Factura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaDAO facturaDAO; 

    @Override
    public List<Factura> findAll() {
        return (List<Factura>) facturaDAO.findAll();
    }

    @Override
    public Factura save(Factura factura) {
        return facturaDAO.save(factura);
    }

    @Override
    public Factura findById(Long id) {
        return facturaDAO.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
       facturaDAO.deleteById(id);
    }
    
}
