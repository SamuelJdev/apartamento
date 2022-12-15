package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Predio;
import sis.apartamento.repository.querys.PredioRepositoryQuery;

public interface PredioRepository extends JpaRepository<Predio, Long>, PredioRepositoryQuery {
}
