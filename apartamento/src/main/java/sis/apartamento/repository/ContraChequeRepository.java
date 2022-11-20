package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.ContraCheque;

public interface ContraChequeRepository extends JpaRepository<ContraCheque, Long> {
}
