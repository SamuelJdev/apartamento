package sis.apartamento.resource.v1.dto.request;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Predio;

@Getter
@Setter
public class AptoRequestPutDTO {

    private Long id;
    private String numeroApto;
    private String statusApartamento = "DISPONIVEL";
    private Predio predio;
}
