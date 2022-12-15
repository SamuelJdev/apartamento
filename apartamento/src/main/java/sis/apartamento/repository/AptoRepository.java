package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Apto;
import sis.apartamento.repository.querys.AptosRepositoryQuery;

public interface AptoRepository extends JpaRepository<Apto, Long>, AptosRepositoryQuery {
}
