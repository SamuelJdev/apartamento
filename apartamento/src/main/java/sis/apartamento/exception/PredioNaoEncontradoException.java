package sis.apartamento.exception;

public class PredioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PredioNaoEncontradoException(String messagem) {
        super(messagem);
    }

    public PredioNaoEncontradoException(Long idPredio) {
        this(String.format("Não existe cadastro de apartamento com o codigo %d", idPredio));
    }
}
