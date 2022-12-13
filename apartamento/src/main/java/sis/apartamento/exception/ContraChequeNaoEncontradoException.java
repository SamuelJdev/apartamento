package sis.apartamento.exception;

public class ContraChequeNaoEncontradoException extends EntidadeNaoEncontradaException{
    public ContraChequeNaoEncontradoException(String mensagem){
        super(mensagem);
    }
    public ContraChequeNaoEncontradoException(Long idContraCheque) {
        this(String.format("Não existe Contracheque codigo %d", idContraCheque));
    }
}
