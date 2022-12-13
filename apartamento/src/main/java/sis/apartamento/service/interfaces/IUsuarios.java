package sis.apartamento.service.interfaces;

import sis.apartamento.model.Usuario;
import java.util.List;

public interface IUsuarios {
    public List<Usuario> listarTodos();
    public Usuario buscarPorId(Long id);
    public Usuario inserir (Usuario usuario);
    public Usuario editar(Usuario usuario, Long id);
    public void deletar(Long id);
}
