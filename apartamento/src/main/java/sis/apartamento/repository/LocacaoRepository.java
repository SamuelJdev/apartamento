package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Locacao;
import sis.apartamento.repository.querys.LocacaoRepositoryQuery;

public interface LocacaoRepository extends JpaRepository<Locacao, Long>, LocacaoRepositoryQuery {
}