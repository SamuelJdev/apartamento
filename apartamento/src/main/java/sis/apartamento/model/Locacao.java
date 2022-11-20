package sis.apartamento.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "locacao")
public class Locacao {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "Nome do Locatario", example = "T. Henrique")
    private String nomeLocatario;

    @ApiModelProperty(value = "Valor Locacao", example = "400")
    private BigDecimal valorLocacao;

    @ApiModelProperty(value = "Valor Pago", example = "400")
    private BigDecimal valorPago = BigDecimal.ZERO;

    @ApiModelProperty(value = "Valor Debito", example = "400")
    private BigDecimal valorDebito = BigDecimal.ZERO;

    @ApiModelProperty(value = "Data da Entrada", example = "2020-10-10")
    private Date dataEntrada;

    @ApiModelProperty(value = "Data da Saida", example = "2020-10-10")
    private Date dataSaida;

    @ApiModelProperty(value = "Status de locacao", example = "Parcial ou Integral")
    private String statusLocacao;

    private Boolean isFechar;

    @ApiModelProperty(value = "Descrição da Locacao", example = "Observação")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "inquilino_id")
    private Inquilino inquilino;

    @ManyToOne
    @JoinColumn(name = "apto_id")
    private Apto apto;

}
