package sis.apartamento.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Predio;
import sis.apartamento.resource.dto.id.PredioIdDTO;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AptoRequestPostDTO {

    @ApiModelProperty(value = "Numero do Apto", example = "2")
    private String numeroApto;

    @ApiModelProperty(value = "Status do Apartamento", example = "Disponivel ou Ocupado")
    private String statusApartamento = "DISPONIVEL";

    @ApiModelProperty(value = "Id do predio", example = "1A")
    private PredioIdDTO predio;
}
