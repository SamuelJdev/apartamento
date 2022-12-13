package sis.apartamento.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.dto.id.InquilinoIdDTO;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContraChequePostDTO {

    @ApiModelProperty(value = "Valor Contra Cheque", example = "1000", required = true)
    private BigDecimal valorContracheque;

    @ApiModelProperty(value = "Data Contra Cheque", example = "2020-10-10", required = true)
    private LocalDate dataContraCheque;

    @ApiModelProperty(value = "Id inquilino", example = "1", required = true)
    private InquilinoIdDTO inquilino;
}
