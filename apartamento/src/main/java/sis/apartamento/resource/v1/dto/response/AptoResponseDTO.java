package sis.apartamento.resource.v1.dto.response;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.resource.v1.dto.id.PredioIdDTO;

@Getter
@Setter
public class AptoResponseDTO {

    private Long id;
    private String numeroApto;
    private String statusApartamento = "DISPONIVEL";
    private PredioIdDTO predio;
}
