package sis.apartamento.resource.v1.dto.response;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.v1.dto.id.InquilinoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class ContraChequeResponseDTO {

    private Long id;
    private BigDecimal valorContracheque;
    private LocalDate dataContraCheque;
    private InquilinoIdDTO inquilino;
}
