package sis.apartamento.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredioRequestPostDTO {

    @ApiModelProperty(value = "Nome do predio", example = "1", required = true)
    private String numeroPredio;

}
