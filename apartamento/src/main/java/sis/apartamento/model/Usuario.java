package sis.apartamento.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "nome do Usuario", example = "Monaco", required = true)
    private String nome;

    @NotNull
    @ApiModelProperty(value = "Sexo do cliente", example = "Masculino ou Feminino", required = true)
    private String sexo;

    @NotNull
    @ApiModelProperty(value = "Senha do Cliente", example = "1234", required = true)
    private String senha;
}
