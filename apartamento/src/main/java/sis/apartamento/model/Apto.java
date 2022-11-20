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
@Table(name = "apto")
public class Apto {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Numero do Apto", example = "2", required = true)
    private String numeroApto;

    @NotNull
    @ApiModelProperty(value = "Status do Apartamento", example = "Disponivel ou Ocupado", required = true)
    private String statusApartamento = "DISPONIVEL";

    @ManyToOne
    @JoinColumn(name = "predio_id")
    private Predio predio;
}