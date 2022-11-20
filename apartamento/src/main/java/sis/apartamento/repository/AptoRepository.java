package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Apto;

public interface AptoRepository extends JpaRepository<Apto, Long> {
}
