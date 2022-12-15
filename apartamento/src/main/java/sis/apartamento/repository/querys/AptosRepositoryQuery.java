package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Apto;
import sis.apartamento.repository.filter.AptosFilter;

public interface AptosRepositoryQuery {
    Page<Apto> filtrar(AptosFilter aptosFilter, Pageable pageable);
}
