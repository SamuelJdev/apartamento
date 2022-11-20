package sis.apartamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sis.apartamento.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
