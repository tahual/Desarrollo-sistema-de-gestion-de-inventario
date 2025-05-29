
import java.util.List;
import java.util.Optional;
import miumg.edu.gt.gestor_de_inventarios.entity.Ordencompra;
import miumg.edu.gt.gestor_de_inventarios.repository.OrdencompraRepository;
import miumg.edu.gt.gestor_de_inventarios.service.OrdencompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


@Service
public class OrdencompraServiceImpl implements OrdencompraService {

    @Autowired
    private OrdencompraRepository ordenRepository;

    @Override
    public List<Ordencompra> findAll() {
        return ordenRepository.findAll();
    }

    @Override
    public Ordencompra findById(Integer id) {
        Optional<Ordencompra> optional = ordenRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Ordencompra save(Ordencompra orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public void delete(Integer id) {
        ordenRepository.deleteById(id);
    }
}