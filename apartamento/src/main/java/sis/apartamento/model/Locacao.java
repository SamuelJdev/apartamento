package sis.apartamento.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    private BigDecimal valorLocacao;
    private BigDecimal valorPago = BigDecimal.ZERO;
    private BigDecimal valorDebito = BigDecimal.ZERO;
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String statusLocacao;
    private Boolean isFechar;
    private BigDecimal valorDiaria = BigDecimal.ZERO;
    private Long dia;
    private Boolean isDiaria;

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
