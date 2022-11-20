package sis.apartamento.service.exceptions;

public class ContraChequeNaoEncontradoException extends RuntimeException{
    public ContraChequeNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
