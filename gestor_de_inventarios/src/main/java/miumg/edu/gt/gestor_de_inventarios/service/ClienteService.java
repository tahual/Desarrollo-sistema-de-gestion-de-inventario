/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miumg.edu.gt.gestor_de_inventarios.service;

import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Cliente;

/**
 *
 * @author danyt
 */
public interface ClienteService {
    List<Cliente> obtenerTodos();
    Optional<Cliente> obtenerPorId(Integer id);
    Cliente guardar(Cliente cliente);
    void eliminar(Integer id);
}