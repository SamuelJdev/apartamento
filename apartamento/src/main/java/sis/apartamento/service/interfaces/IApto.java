package sis.apartamento.service.interfaces;

import sis.apartamento.model.Apto;
import java.util.List;

public interface IApto {
    List<Apto> listarTodos();
    Apto buscarPorId(Long id);
    Apto inserir (Apto apto);
    Apto editar(Apto apto);
    Apto editar(Apto apto, Long id);
    void deletar(Long id);
}
