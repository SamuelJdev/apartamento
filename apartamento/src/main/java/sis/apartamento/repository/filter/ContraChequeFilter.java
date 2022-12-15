package sis.apartamento.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ContraChequeFilter {

    private BigDecimal valorContracheque;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataContraCheque;
}
