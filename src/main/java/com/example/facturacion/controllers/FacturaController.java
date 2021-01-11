package com.example.facturacion.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.facturacion.entities.Cliente;
import com.example.facturacion.entities.Factura;
import com.example.facturacion.entities.ItemFactura;
import com.example.facturacion.entities.Producto;
import com.example.facturacion.response.ClienteResponse;
import com.example.facturacion.response.ItemFacturaResponse;
import com.example.facturacion.service.ClienteService;
import com.example.facturacion.service.FacturaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturas")
@CrossOrigin(origins = { "http://localhost:4200" })
public class FacturaController {

    @Autowired
    private FacturaService facturaServiceImp;

    @Autowired
    private ClienteService clienteServiceImp;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public List<Factura> index() {
        return facturaServiceImp.findAll();
    }

    // falta probarlo
    @PostMapping("/create/{cliente_id}")
    private ResponseEntity<?> create(@Valid @RequestBody Factura factura, @RequestBody Long[] itemid,
            @RequestBody Integer[] cantidad, BindingResult result, @PathVariable Long cliente_id) {
        Factura facturaN = null;
        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo: '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("error", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (itemid == null || itemid.length == 0) {
            List<String> errors = result.getFieldErrors().stream().map(err -> "La factura no puede estar vacia")
                    .collect(Collectors.toList());
            response.put("error", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < itemid.length; i++) {
            Producto producto = clienteServiceImp.findProductoById(itemid[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            factura.addItemFactura(linea);
            log.info("ID: " + itemid[i].toString() + ", cantidad; " + cantidad[i].toString());
        }

        try {
            facturaN = clienteServiceImp.saveFactura(factura);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al crear la factura en la base de datos");
            response.put("error", "Error al crear la factura en la base de datos " + e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La Factura ha sido creada con exito");
        response.put("factura", facturaN);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/ver/{id}")
    public ResponseEntity<?> ver(@PathVariable Long id) {
        // Factura factura = clienteServiceImp.findFacturaById(id);
        Factura factura = clienteServiceImp.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        Map<String, Object> response = new HashMap<>();
        if (factura == null) {
            response.put("error", "La factura no existe en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setApellido(factura.getCliente().getApellido());
        clienteResponse.setNombre(factura.getCliente().getNombre());
        clienteResponse.setId(factura.getCliente().getId());
        clienteResponse.setEmail(factura.getCliente().getEmail());
        
        List<ItemFacturaResponse> items = new ArrayList<ItemFacturaResponse>();
        ItemFacturaResponse item = null;
        for(int i = 0; i < factura.getItems().size(); i++){
            item = new ItemFacturaResponse();
            log.info("NUMERO DE ITEMS " + factura.getItems().size() + " I = " + i);
            log.info("ID: "+ factura.getItems().get(i).getId() + " Producto "  + factura.getItems().get(i).getProducto().getNombre());
            item.setId(factura.getItems().get(i).getId());
            item.setCantidad(factura.getItems().get(i).getCantidad());
            item.setProducto(factura.getItems().get(i).getProducto());
            item.setTotal(factura.getItems().get(i).calcularImporte());
            items.add(item);
        }


        response.put("factura", factura);
        response.put("cliente", clienteResponse);
        response.put("items", items);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

    }
}
