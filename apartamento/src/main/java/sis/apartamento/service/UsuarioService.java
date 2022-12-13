package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.exception.EntidadeEmUsoException;
import sis.apartamento.exception.UsuarioNaoEncontradoException;
import sis.apartamento.model.Usuario;
import sis.apartamento.repository.UsuarioRepository;
import sis.apartamento.service.interfaces.IUsuarios;
import java.util.List;

@Service
public class UsuarioService implements IUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }
    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNaoEncontradoException(id));
    }
    @Override
    public Usuario inserir (Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    @Override
    public Usuario editar(Usuario usuario, Long id){
        verificarExistencia(id);
        usuarioRepository.save(usuario);
        return usuario;
    }

    private void verificarExistencia(Long id){
        buscarPorId(id);
    }
    @Override
    public void deletar(Long id){
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UsuarioNaoEncontradoException(id);
        }catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Usuario com codigo %d não pode ser removido, pois esta em uso", id));
        }
    }
}