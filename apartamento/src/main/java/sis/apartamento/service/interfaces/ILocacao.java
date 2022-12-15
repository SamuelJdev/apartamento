package sis.apartamento.service.interfaces;

import sis.apartamento.model.Locacao;

import java.util.List;

public interface ILocacao {
    List<Locacao> listarTodos();
    Locacao buscarPorId(Long id);
    Locacao inserir(Locacao locacao);
    Locacao editar(Locacao locacao, Long id);
    void deletar(Long id);
}
