package sis.apartamento.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import sis.apartamento.model.Usuario;
import sis.apartamento.repository.UsuarioRepository;
import sis.apartamento.service.exceptions.UsuarioNaoEncontradoException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("O usuario não pode ser localizado!");
        }
        return usuario;
    }

    public Usuario inserir (Usuario usuario){
        usuario.setId(null);
        return usuarioRepository.save(usuario);
    }

    public void editar(Usuario usuario){
        verificarExistencia(usuario);
        usuarioRepository.save(usuario);
    }

    private void verificarExistencia(Usuario usuario){
        buscarPorId(usuario.getId());
    }

    public void deletar(Long id){
        try {
            usuarioRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new UsuarioNaoEncontradoException("O usuario não pode ser localizado!");
        }
    }
}
