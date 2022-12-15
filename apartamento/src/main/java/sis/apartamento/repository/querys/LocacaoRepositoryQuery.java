package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Locacao;
import sis.apartamento.repository.filter.LocacaoFilter;

public interface LocacaoRepositoryQuery {
    Page<Locacao> filtrar(LocacaoFilter locacaoFilter, Pageable pageable);
}
