package sis.apartamento.resource.v1.dto.request;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.v1.dto.id.ApartamentoIdDTO;
import sis.apartamento.resource.v1.dto.id.InquilinoIdDTO;
import sis.apartamento.resource.v1.dto.id.UsuarioIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LocacaoRequestPutDTO {

    private Long id;
    private BigDecimal valorLocacao;
    private BigDecimal valorPago = BigDecimal.ZERO;
    private BigDecimal valorDebito = BigDecimal.ZERO; // SISTEMA
    private LocalDate dataEntrada;
    private LocalDate dataSaida;
    private String statusLocacao; // SISTEMA
    private Boolean isFechar; // SISTEMA
    private BigDecimal valorDiaria = BigDecimal.ZERO; // SISTEMA
    private Long dia; // SISTEMA
    private Boolean isDiaria = true; // SISTEMA
    private UsuarioIdDTO usuario;
    private InquilinoIdDTO inquilino;
    private ApartamentoIdDTO apto;
}
