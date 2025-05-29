/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Reporte;

/**
 *
 * @author danyt
 */

public interface ReporteService {
    List<Reporte> findAll();
    Optional<Reporte> findById(Integer id);
    Reporte save(Reporte reporte);
    void deleteById(Integer id);
}