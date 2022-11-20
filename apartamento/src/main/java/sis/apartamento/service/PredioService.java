package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.Predio;
import sis.apartamento.repository.PredioRepository;
import sis.apartamento.service.exceptions.PredioNaoEncontradoException;
import java.util.List;

@Service
public class PredioService {
    @Autowired
    private PredioRepository predioRepository;

    public List<Predio> listarTodos(){
        return predioRepository.findAll();
    }

    public Predio buscarPorId(Long id) {
        Predio predio = predioRepository.findById(id).orElse(null);
        if (predio == null) {
            throw new PredioNaoEncontradoException("O Predio não foi encontrato!");
        }
        return predio;
    }

    public Predio inserir (Predio predio){
        predio.setId(null);
        return predioRepository.save(predio);
    }

    public void editar(Predio predio ){
        verificarExistencia(predio);
        predioRepository.save(predio);
    }

    private void verificarExistencia(Predio predio){
        buscarPorId(predio.getId());
    }

    public void deletar(Long id){
        try {
            predioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new PredioNaoEncontradoException("O Predio não foi encontrato!");
        }
    }
}
