package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Inquilino;
import sis.apartamento.repository.filter.InquilinoFilter;

public interface InquilinoRepositoryQuery {
    Page<Inquilino> filtrar(InquilinoFilter inquilinoFilter, Pageable pageable);
}
