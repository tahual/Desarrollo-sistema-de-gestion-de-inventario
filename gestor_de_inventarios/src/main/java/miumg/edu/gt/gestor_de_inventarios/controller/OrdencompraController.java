/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.controller;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
import miumg.edu.gt.gestor_de_inventarios.service.OrdencompraService;
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
@RequestMapping("/api/ordenes")
@CrossOrigin(origins = "*")
public class OrdencompraController {

    @Autowired
    private OrdencompraService ordencompraService;

    @GetMapping
    public List<Ordencompra> getAllOrdenes() {
        return ordencompraService.findAll();
    }

    @GetMapping("/{id}")
    public Ordencompra getOrdenById(@PathVariable Integer id) {
        return ordencompraService.findById(id);
    }

    @PostMapping
    public Ordencompra createOrden(@RequestBody Ordencompra orden) {
        return ordencompraService.save(orden);
    }

    @PutMapping("/{id}")
    public Ordencompra updateOrden(@PathVariable Integer id, @RequestBody Ordencompra orden) {
        orden.setIdordencompra(id);
        return ordencompraService.save(orden);
    }

    @DeleteMapping("/{id}")
    public void deleteOrden(@PathVariable Integer id) {
        ordencompraService.delete(id);
    }
}