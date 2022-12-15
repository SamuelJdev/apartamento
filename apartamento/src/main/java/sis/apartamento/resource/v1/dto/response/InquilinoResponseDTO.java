package sis.apartamento.resource.v1.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class InquilinoResponseDTO {

    private Long id;

    private String nome;

    private String cpf;

    private BigDecimal valorContraCheque;
}
