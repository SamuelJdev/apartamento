package sis.apartamento.resource.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
public class LocacaoRequestPostDTO {

    @ApiModelProperty(value = "Valor Locacao", example = "270,00", position = 2)
    private BigDecimal valorLocacao = BigDecimal.ZERO;

    @ApiModelProperty(value = "Valor Locacao", example = "260,00", position = 3)
    private BigDecimal valorPago = BigDecimal.ZERO;

    @ApiModelProperty(value = "Valor da Diaria ou Valor Mensal", example = "9 ou 300")
    private Boolean isDiaria = true;

    @ApiModelProperty(value = "Data de entrada", example = "2020-10-10")
    private LocalDate dataEntrada;

    @ApiModelProperty(value = "Data de saida", example = "2020-10-10")
    private LocalDate dataSaida;

    @ApiModelProperty(value = "ID do inquilino", example = "1", position = 5)
    private InquilinoIdDTO inquilino;

    @ApiModelProperty(value = "ID do apartamento", example = "1", position = 6)
    private ApartamentoIdDTO apto;

    @ApiModelProperty(value = "ID do usuário", example = "1", position = 4)
    private UsuarioIdDTO usuario;

}
