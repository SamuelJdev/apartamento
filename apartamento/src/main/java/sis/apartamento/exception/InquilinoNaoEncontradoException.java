package sis.apartamento.exception;

public class InquilinoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public InquilinoNaoEncontradoException(String messagem) {
        super(messagem);
    }
    public InquilinoNaoEncontradoException(Long idInquilino) {
        this(String.format("Não existe Inquilino no codigo %d", idInquilino));
    }
}
