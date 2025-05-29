/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Venta;

/**
 *
 * @author danyt
 */
public interface VentaService {
    List<Venta> findAll();
    Optional<Venta> findById(Integer id);
    Venta save(Venta venta);
    void deleteById(Integer id);
}