/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.ProveedorProducto;
import miumg.edu.gt.gestor_de_inventarios.repository.ProveedorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class ProveedorProductoServiceImpl implements ProveedorProductoService {

    @Autowired
    private ProveedorProductoRepository repository;

    @Override
    public List<ProveedorProducto> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ProveedorProducto> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public ProveedorProducto save(ProveedorProducto proveedorProducto) {
        return repository.save(proveedorProducto);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}