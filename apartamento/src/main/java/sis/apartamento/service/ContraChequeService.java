package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.repository.ContraChequeRepository;
import sis.apartamento.service.exceptions.ContraChequeNaoEncontradoException;
import java.util.List;

@Service
public class ContraChequeService {

    @Autowired
    private ContraChequeRepository contraChequeRepository;

    public List<ContraCheque> listarTodos(){
        return contraChequeRepository.findAll();
    }

    public ContraCheque buscarPorId(Long id) {
        ContraCheque contraCheque = contraChequeRepository.findById(id).orElse(null);
        if (contraCheque == null) {
            throw new ContraChequeNaoEncontradoException("O ContraCheque não foi encontrado!");
        }
        return contraCheque;
    }

    public ContraCheque inserir (ContraCheque contraCheque){
        contraCheque.setId(null);
        return contraChequeRepository.save(contraCheque);
    }

    public void editar(ContraCheque contraCheque){
        verificarExistencia(contraCheque);
        contraChequeRepository.save(contraCheque);
    }

    private void verificarExistencia(ContraCheque contraCheque){
        buscarPorId(contraCheque.getId());
    }

    public void deletar(Long id){
        try {
            contraChequeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ContraChequeNaoEncontradoException("O ContraCheque não foi encontrado!");
        }
    }
}
