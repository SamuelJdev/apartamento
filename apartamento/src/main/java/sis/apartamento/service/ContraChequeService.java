package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.ContraChequeNaoEncontradoException;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.repository.ContraChequeRepository;
import sis.apartamento.service.interfaces.IContraCheque;
import java.util.List;

@Service
public class ContraChequeService implements IContraCheque {

    @Autowired
    private ContraChequeRepository contraChequeRepository;
    @Override
    public List<ContraCheque> listarTodos(){
        return contraChequeRepository.findAll();
    }
    @Override
    public ContraCheque buscarPorId(Long id) {
        return contraChequeRepository.findById(id).orElseThrow(()-> new ContraChequeNaoEncontradoException(id));
    }
    @Override
    public ContraCheque inserir (ContraCheque contraCheque){
        return contraChequeRepository.save(contraCheque);

    }
    @Override
    public ContraCheque editar(ContraCheque contraCheque, Long id){
        verificarExistencia(id);
        contraChequeRepository.save(contraCheque);
        return contraCheque;
    }

    private void verificarExistencia(Long id){
        buscarPorId(id);
    }
    @Override
    public void deletar(Long id){
        try {
            contraChequeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ContraChequeNaoEncontradoException(id);
        }
    }
}