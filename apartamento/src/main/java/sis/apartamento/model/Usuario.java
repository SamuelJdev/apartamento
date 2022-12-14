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
@Entity   /* para dizer que a classe Usuario é uma entidade*/
@Table(name = "usuario")
public class Usuario {

    /* Classe seguindo o padrão javaBeans, contendo todos os atributos privados, getters e setters para seus atributos,
     Usada para encapsular e abstrair uma entidade, Implementa java.io.Serializable */

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sexo;

    @NotNull
    private String senha;
}
