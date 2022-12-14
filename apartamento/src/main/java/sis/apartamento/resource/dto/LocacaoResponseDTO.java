package sis.apartamento.resource.dto;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.dto.id.ApartamentoIdDTO;
import sis.apartamento.resource.dto.id.InquilinoIdDTO;
import sis.apartamento.resource.dto.id.UsuarioIdDTO;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class LocacaoResponseDTO {

    private Long id;
    private BigDecimal valorLocacao;
    private BigDecimal valorPago = BigDecimal.ZERO;
    private BigDecimal valorDebito = BigDecimal.ZERO;
    private Date dataEntrada;
    private Date dataSaida;
    private String statusLocacao;
    private Boolean isFechar;
    private UsuarioIdDTO usuario;
    private InquilinoIdDTO inquilino;
    private ApartamentoIdDTO apto;
}
