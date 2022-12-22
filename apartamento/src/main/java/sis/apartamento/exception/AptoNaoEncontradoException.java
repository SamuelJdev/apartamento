package sis.apartamento.exception;


public class AptoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public AptoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public AptoNaoEncontradoException(Long idApartamento) {
        this(String.format("Não existe cadastro de apartamento com o codigo %d", idApartamento));
    }
}