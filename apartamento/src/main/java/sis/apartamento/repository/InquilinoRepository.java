package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Inquilino;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
}
