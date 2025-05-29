/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.controller;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Reporte;
import miumg.edu.gt.gestor_de_inventarios.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public List<Reporte> getAll() {
        return reporteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getById(@PathVariable Integer id) {
        Optional<Reporte> reporte = reporteService.findById(id);
        return reporte.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reporte create(@RequestBody Reporte reporte) {
        return reporteService.save(reporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> update(@PathVariable Integer id, @RequestBody Reporte reporteDetails) {
        Optional<Reporte> optionalReporte = reporteService.findById(id);
        if (!optionalReporte.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Reporte reporte = optionalReporte.get();
        reporte.setTiporeporte(reporteDetails.getTiporeporte());
        reporte.setContenido(reporteDetails.getContenido());
        reporte.setFechageneracion(reporteDetails.getFechageneracion());
        reporte.setIdusuario(reporteDetails.getIdusuario());

        return ResponseEntity.ok(reporteService.save(reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!reporteService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reporteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
