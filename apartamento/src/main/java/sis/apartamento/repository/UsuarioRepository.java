package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Usuario;
import sis.apartamento.repository.querys.UsuarioRepositoryQuery;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>, UsuarioRepositoryQuery {
}
