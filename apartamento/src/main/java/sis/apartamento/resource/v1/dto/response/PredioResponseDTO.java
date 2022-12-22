package sis.apartamento.resource.v1.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PredioResponseDTO {

    private Long id;
    private String numeroPredio;
    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String uf;
}
