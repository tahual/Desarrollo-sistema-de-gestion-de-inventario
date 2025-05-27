/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Factura;
import miumg.edu.gt.gestor_de_inventarios.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author danyt
 */
@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura findById(Integer id) {
        Optional<Factura> optional = facturaRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public void delete(Integer id) {
        facturaRepository.deleteById(id);
    }
}
