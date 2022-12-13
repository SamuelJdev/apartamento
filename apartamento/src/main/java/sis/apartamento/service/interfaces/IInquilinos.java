package sis.apartamento.service.interfaces;

import sis.apartamento.model.Inquilino;
import java.util.List;

public interface IInquilinos {
    List<Inquilino> listarTodos();
    Inquilino buscarPorId(Long id) ;
    Inquilino inserir (Inquilino inquilino);
    Inquilino editar(Inquilino inquilino, Long id);
    void deletar(Long id);
}
