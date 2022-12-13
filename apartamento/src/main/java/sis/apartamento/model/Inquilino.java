package sis.apartamento.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Inquilino")
public class Inquilino implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Nome do Inquilino", example = "T. Henrique", required = true, position = 1)
    private String nome;

    @NotNull
    @Column(unique = true)
    @ApiModelProperty(value = "CPF do Inquilino", example = "12345678911", required = true, position = 2)
    private String cpf;

    @NotNull
    @ApiModelProperty(value = "Valor Contra Cheque", example = "1200", required = true, position = 3)
    private BigDecimal valorContraCheque;
}