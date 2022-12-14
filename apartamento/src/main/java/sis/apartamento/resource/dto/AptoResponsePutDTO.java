package sis.apartamento.resource.dto;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Predio;

@Getter
@Setter
public class AptoResponsePutDTO {

    private Long id;
    private String numeroApto;
    private String statusApartamento = "DISPONIVEL";
    private Predio predio;
}
