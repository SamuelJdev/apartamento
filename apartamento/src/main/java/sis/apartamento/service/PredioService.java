package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.PredioNaoEncontradoException;
import sis.apartamento.model.Predio;
import sis.apartamento.repository.PredioRepository;
import sis.apartamento.service.interfaces.IPredio;
import java.util.List;

@Service
public class PredioService implements IPredio {
    @Autowired
    private PredioRepository predioRepository;
    @Override
    public List<Predio> listarTodos(){
        return predioRepository.findAll();
    }
    @Override
    public Predio buscarPorId(Long id) {
        return predioRepository.findById(id)
                .orElseThrow(()-> new PredioNaoEncontradoException(id));
    }
    @Override
    public Predio inserir (Predio predio){
        return predioRepository.save(predio);
    }
    @Override
    public Predio editar(Predio predio, Long id){
        verificarExistencia(id);
        predioRepository.save(predio);
        return predio;
    }
    private void verificarExistencia(Long id){
        buscarPorId(id);
    }
    @Override
    public void deletar(Long id){
        try {
            predioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new PredioNaoEncontradoException(id);
        }
    }
}
