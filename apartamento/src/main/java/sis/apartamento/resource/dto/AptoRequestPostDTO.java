package sis.apartamento.resource.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import sis.apartamento.model.Predio;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AptoRequestPostDTO {

    private Long id;

    private String numeroApto;

    private String statusApartamento = "DISPONIVEL";

    private Predio predio;
}
