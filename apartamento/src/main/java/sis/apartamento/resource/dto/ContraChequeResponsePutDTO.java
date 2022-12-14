package sis.apartamento.resource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.dto.id.InquilinoIdDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContraChequeResponsePutDTO {

    private Long id;
    private BigDecimal valorContracheque;
    private LocalDate dataContraCheque;
    private InquilinoIdDTO inquilino;
}
