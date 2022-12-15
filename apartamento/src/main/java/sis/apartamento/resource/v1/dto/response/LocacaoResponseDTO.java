package sis.apartamento.resource.v1.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.v1.dto.id.ApartamentoIdDTO;
import sis.apartamento.resource.v1.dto.id.InquilinoIdDTO;
import sis.apartamento.resource.v1.dto.id.UsuarioIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocacaoResponseDTO {

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
    private Boolean isDiaria = true;
    private UsuarioIdDTO usuario;
    private InquilinoIdDTO inquilino;
    private ApartamentoIdDTO apto;
}
