package sis.apartamento.resource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Inquilino;
import sis.apartamento.resource.dto.id.InquilinoIdDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContraChequePutDTO {

    private Long id;
    private BigDecimal valorContracheque;
    private LocalDate dataContraCheque;
    private InquilinoIdDTO inquilino;

}
