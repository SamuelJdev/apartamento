package sis.apartamento.repository.querys;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sis.apartamento.model.Usuario;
import sis.apartamento.repository.filter.UsuarioFilter;

public interface UsuarioRepositoryQuery {
    Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable);
}
