package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Locacao;

public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
}