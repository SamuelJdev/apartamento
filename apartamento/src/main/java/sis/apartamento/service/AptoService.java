package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.Apto;
import sis.apartamento.repository.AptoRepository;
import sis.apartamento.service.exceptions.AptoNaoEncontradoException;
import java.util.List;

@Service
public class AptoService {

    @Autowired
    private AptoRepository aptoRepository;

    public List<Apto> listarTodos(){
        return aptoRepository.findAll();
    }

    public Apto buscarPorId(Long id) {
        Apto apto = aptoRepository.findById(id).orElse(null);
        if (apto == null) {
            throw new AptoNaoEncontradoException("O Apartamento não encontrato!");
        }
        return apto;
    }

    public Apto inserir (Apto apto){
        apto.setId(null);
        return aptoRepository.save(apto);
    }

    public void editar(Apto apto){
        verificarExistencia(apto);
        aptoRepository.save(apto);
    }

    private void verificarExistencia(Apto apto){
        buscarPorId(apto.getId());
    }

    public void deletar(Long id){
        try {
            aptoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new AptoNaoEncontradoException("O Apartamento não encontrato!");
        }
    }
}