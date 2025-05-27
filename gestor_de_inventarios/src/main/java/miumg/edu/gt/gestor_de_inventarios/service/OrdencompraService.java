/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;



/**
 *
 * @author danyt
 */
public interface OrdencompraService {
    
    List<Ordencompra> findAll(); 
    Ordencompra finById(Integer id); 
    Ordencompra save(Ordencompra oreden); 
    void delete(Integer id); 
}
