package sis.apartamento.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "apto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Apto {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String numeroApto;

    @NotNull
    private String statusApartamento = "DISPONIVEL";

    @ManyToOne
    @JoinColumn(name = "predio_id")
    private Predio predio;
}