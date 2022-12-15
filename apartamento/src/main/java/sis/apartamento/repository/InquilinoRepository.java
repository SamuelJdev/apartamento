package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Inquilino;
import sis.apartamento.repository.querys.InquilinoRepositoryQuery;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long>, InquilinoRepositoryQuery {
}
