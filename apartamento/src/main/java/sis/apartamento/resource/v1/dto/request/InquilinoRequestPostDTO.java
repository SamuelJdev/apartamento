package sis.apartamento.resource.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InquilinoRequestPostDTO {

    @ApiModelProperty(value = "Nome do Inquilino", example = "T. Henrique", position = 1)
    private String nome;

    @ApiModelProperty(value = "CPF do Inquilino", example = "12345678911", position = 2)
    private String cpf;

    @ApiModelProperty(value = "Valor Contra Cheque", example = "1200", position = 3)
    private BigDecimal valorContraCheque;
}