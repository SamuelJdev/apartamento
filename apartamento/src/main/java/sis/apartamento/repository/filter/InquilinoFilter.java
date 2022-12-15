package sis.apartamento.repository.filter;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InquilinoFilter {
    private String nome;
    private String cpf;
    private BigDecimal valorContraCheque;
}
