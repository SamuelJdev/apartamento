package sis.apartamento.service.interfaces;

import sis.apartamento.model.Predio;
import java.util.List;

public interface IPredio {

    List<Predio> listarTodos();

    Predio buscarPorId(Long id);

    Predio inserir (Predio predio);

    Predio editar(Predio predio, Long id);

    void deletar(Long id);
}
