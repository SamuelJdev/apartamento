package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Predio;

public interface PredioRepository extends JpaRepository<Predio, Long> {
}
