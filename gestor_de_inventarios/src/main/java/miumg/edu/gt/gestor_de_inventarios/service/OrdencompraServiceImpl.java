/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
import miumg.edu.gt.gestor_de_inventarios.repository.OrdencompraRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author danyt
 */
@service
public class OrdencompraServiceImpl implements OrdencompraService{
    
    @Autowired
    private OrdencompraRepository ordenRepository; 
    

    @Override
    public List<Ordencompra> findAll() {
        return ordenRepository.findAll(); 
    }

    @Override
    public Ordencompra finById(Integer id) {
        
  }

    @Override
    public Ordencompra save(Ordencompra oreden) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
