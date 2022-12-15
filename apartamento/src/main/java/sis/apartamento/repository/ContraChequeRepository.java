package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.ContraCheque;
import sis.apartamento.repository.querys.ContraChequeRepositoryQuery;

public interface ContraChequeRepository extends JpaRepository<ContraCheque, Long>, ContraChequeRepositoryQuery {
}
