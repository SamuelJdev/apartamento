package sis.apartamento.repository.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class LocacaoFilter {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEntrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataSaida;

    private String statusLocacao;
}
