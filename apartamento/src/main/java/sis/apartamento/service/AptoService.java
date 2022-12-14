package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.AptoNaoEncontradoException;
import sis.apartamento.model.Apto;
import sis.apartamento.repository.AptoRepository;
import sis.apartamento.service.interfaces.IApto;

import java.util.List;

@Service
public class AptoService implements IApto {

    @Autowired
    private AptoRepository aptoRepository;

    @Override
    public List<Apto> listarTodos() {
        return aptoRepository.findAll();
    }

    @Override
    public Apto buscarPorId(Long id){
        return aptoRepository.findById(id)
                .orElseThrow(()-> new AptoNaoEncontradoException(id));
    }
    @Override
    public Apto inserir (Apto apto){
        return aptoRepository.save(apto);
    }
    @Override
    public Apto editar(Apto apto){
        aptoRepository.save(apto);
        return apto;
    }
    @Override
    public Apto editar(Apto apto, Long id){
        verificarExistencia(id);
        aptoRepository.save(apto);
        return apto;
    }

    private void verificarExistencia(Long id){
        buscarPorId(id);
    }

    @Override
    public void deletar(Long id){
        try {
            aptoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new AptoNaoEncontradoException(id);
        }
    }
}