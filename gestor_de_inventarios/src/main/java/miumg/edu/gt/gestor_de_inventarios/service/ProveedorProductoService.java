/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.ProveedorProducto;

/**
 *
 * @author danyt
 */

public interface ProveedorProductoService {
    List<ProveedorProducto> findAll();
    Optional<ProveedorProducto> findById(Integer id);
    ProveedorProducto save(ProveedorProducto proveedorProducto);
    void deleteById(Integer id);
}
