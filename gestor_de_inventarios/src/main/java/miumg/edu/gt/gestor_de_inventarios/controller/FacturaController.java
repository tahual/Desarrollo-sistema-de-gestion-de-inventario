/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.controller;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;
import miumg.edu.gt.gestor_de_inventarios.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author danyt
 */
@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @GetMapping
    public List<Factura> getAllFacturas() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public Factura getFacturaById(@PathVariable Integer id) {
        return facturaService.findById(id);
    }

    @PostMapping
    public Factura createFactura(@RequestBody Factura factura) {
        return facturaService.save(factura);
    }

    @PutMapping("/{id}")
    public Factura updateFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        factura.setIdfactura(id);
        return facturaService.save(factura);
    }

    @DeleteMapping("/{id}")
    public void deleteFactura(@PathVariable Integer id) {
        facturaService.delete(id);
    }
}