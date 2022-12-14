package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.InquilinoNaoEncontradoException;
import sis.apartamento.model.Inquilino;
import sis.apartamento.repository.InquilinoRepository;
import sis.apartamento.service.interfaces.IInquilinos;
import java.util.List;

@Service
public class InquilinoService implements IInquilinos {
    @Autowired
    private InquilinoRepository inquilinoRepository;
    @Override
    public List<Inquilino> listarTodos(){
        return inquilinoRepository.findAll();
    }
    @Override
    public Inquilino buscarPorId(Long id) {
        return inquilinoRepository.findById(id)
                .orElseThrow(()-> new InquilinoNaoEncontradoException(id));
    }
    @Override
    public Inquilino inserir (Inquilino inquilino){
        return inquilinoRepository.save(inquilino);
    }
    @Override
    public Inquilino editar(Inquilino inquilino, Long id){
        verificarExistencia(id);
        return inquilinoRepository.save(inquilino);
    }

    private void verificarExistencia(Long id){
        buscarPorId(id);
    }
    @Override
    public void deletar(Long id){
        try {
            inquilinoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new InquilinoNaoEncontradoException(id);
        }
    }
}