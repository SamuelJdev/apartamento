package sis.apartamento.resource.dto;

import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Predio;
import sis.apartamento.resource.dto.id.PredioIdDTO;

@Getter
@Setter
public class AptoResponseDTO {

    private Long id;
    private String numeroApto;
    private String statusApartamento = "DISPONIVEL";
    private PredioIdDTO predio;
}
