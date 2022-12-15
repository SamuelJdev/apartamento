package sis.apartamento.resource.v1.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioRequestPostDTO {

    @ApiModelProperty(value = "nome do Usuario", example = "Monaco")
    private String nome;

    @ApiModelProperty(value = "Sexo do cliente", example = "Masculino ou Feminino")
    private String sexo;

    @ApiModelProperty(value = "Senha do Cliente", example = "1234")
    private String senha;
}
