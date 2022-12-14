package sis.apartamento.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.dto.id.ApartamentoIdDTO;
import sis.apartamento.resource.dto.id.InquilinoIdDTO;
import sis.apartamento.resource.dto.id.UsuarioIdDTO;

import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocacaoRequestPostDTO {

    @ApiModelProperty(value = "Valor Locacao", example = "270,00", position = 2)
    private BigDecimal valorLocacao = BigDecimal.ZERO;

    @ApiModelProperty(value = "Valor Locacao", example = "260,00", position = 3)
    private BigDecimal valorPago = BigDecimal.ZERO;

    @ApiModelProperty(value = "ID do usuário", example = "1", position = 4)
    private UsuarioIdDTO usuario;

    @ApiModelProperty(value = "ID do inquilino", example = "1", position = 5)
    private InquilinoIdDTO inquilino;

    @ApiModelProperty(value = "ID do apartamento", example = "1", position = 6)
    private ApartamentoIdDTO apto;
}
