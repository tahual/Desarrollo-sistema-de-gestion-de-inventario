/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;

/**
 *
 * @author danyt
 */
public interface FacturaService {
    List<Factura> findAll();
    Factura findById(Integer id);
    Factura save(Factura factura);
    void delete(Integer id);
}