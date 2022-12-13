package sis.apartamento.resource.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;

    private String nome;

    private String sexo;

    private String senha;
}
