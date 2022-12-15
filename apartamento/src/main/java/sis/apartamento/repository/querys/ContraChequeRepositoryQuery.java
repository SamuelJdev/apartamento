package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.repository.filter.ContraChequeFilter;

public interface ContraChequeRepositoryQuery {
    Page<ContraCheque> filtrar(ContraChequeFilter contraChequeFilter, Pageable pageable);
}
