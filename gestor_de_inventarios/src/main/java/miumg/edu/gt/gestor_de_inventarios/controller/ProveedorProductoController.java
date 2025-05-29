/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.controller;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.ProveedorProducto;
import miumg.edu.gt.gestor_de_inventarios.service.ProveedorProductoService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/proveedor-producto")
public class ProveedorProductoController {

    @Autowired
    private ProveedorProductoService service;

    @GetMapping
    public List<ProveedorProducto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ProveedorProducto> getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ProveedorProducto create(@RequestBody ProveedorProducto proveedorProducto) {
        return service.save(proveedorProducto);
    }

    @PutMapping("/{id}")
    public ProveedorProducto update(@PathVariable Integer id, @RequestBody ProveedorProducto proveedorProducto) {
        proveedorProducto.setIdProveedorProducto(id);
        return service.save(proveedorProducto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
