/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Proveedor;



/**
 *
 * @author danyt
 */
public interface ProveedorService {
    List<Proveedor> findAll();
    Proveedor findById(Integer id);
    Proveedor save(Proveedor proveedor);
    void delete(Integer id);
}

