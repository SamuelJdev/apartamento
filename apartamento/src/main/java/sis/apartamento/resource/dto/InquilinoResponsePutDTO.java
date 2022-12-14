package sis.apartamento.resource.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class InquilinoResponsePutDTO {

    private Long id;
    private String nome;
    private String cpf;
    private BigDecimal valorContraCheque;
}
