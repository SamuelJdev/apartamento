package sis.apartamento.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "contra_cheque")
public class ContraCheque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ApiModelProperty(value = "Valor Contra Cheque", example = "1000", required = true)
    private BigDecimal valorContracheque;

    @NotNull
    @ApiModelProperty(value = "Data Contra Cheque", example = "2020-10-10", required = true)
    private LocalDate dataContraCheque;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "inquilino_id")
    private Inquilino inquilino;
}