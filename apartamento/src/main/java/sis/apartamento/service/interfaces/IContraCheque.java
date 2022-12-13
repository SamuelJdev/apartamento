package sis.apartamento.service.interfaces;

import sis.apartamento.model.ContraCheque;

import java.util.List;

public interface IContraCheque {
    List<ContraCheque> listarTodos();
    ContraCheque buscarPorId(Long id);
    ContraCheque inserir (ContraCheque contraCheque);
    ContraCheque editar(ContraCheque contraCheque, Long id);
    void deletar(Long id);
}
