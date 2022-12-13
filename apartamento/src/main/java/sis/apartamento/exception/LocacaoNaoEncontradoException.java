package sis.apartamento.exception;

public class LocacaoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public LocacaoNaoEncontradoException(String messagem) {
        super(messagem);
    }
    public LocacaoNaoEncontradoException(Long idLocao) {
        this(String.format("Não existe Locação no codigo %d", idLocao));
    }
}
