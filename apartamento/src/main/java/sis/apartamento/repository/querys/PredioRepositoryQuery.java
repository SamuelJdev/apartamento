package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Predio;
import sis.apartamento.repository.filter.PredioFilter;

public interface PredioRepositoryQuery {
    Page<Predio> filtrar(PredioFilter predioFilter, Pageable pageable);
}
