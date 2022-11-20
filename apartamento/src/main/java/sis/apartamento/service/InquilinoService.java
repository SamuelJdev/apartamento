package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.Inquilino;
import sis.apartamento.repository.InquilinoRepository;
import sis.apartamento.service.exceptions.InquilinoNaoEncontradoException;
import java.util.List;

@Service
public class InquilinoService {

    @Autowired
    private InquilinoRepository inquilinoRepository;

    public List<Inquilino> listarTodos(){
        return inquilinoRepository.findAll();
    }

    public Inquilino buscarPorId(Long id) {
        Inquilino inquilino = inquilinoRepository.findById(id).orElse(null);
        if (inquilino == null) {
            throw new InquilinoNaoEncontradoException("O inquilino não foi encontrado!");
        }
        return inquilino;
    }

    public Inquilino inserir (Inquilino inquilino){
        inquilino.setId(null);
        return inquilinoRepository.save(inquilino);
    }

    public void editar(Inquilino inquilino){
        verificarExistencia(inquilino);
        inquilinoRepository.save(inquilino);
    }

    private void verificarExistencia(Inquilino inquilino){
        buscarPorId(inquilino.getId());
    }

    public void deletar(Long id){
        try {
            inquilinoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new InquilinoNaoEncontradoException("O inquilino não foi encontrado!");
        }
    }
}
