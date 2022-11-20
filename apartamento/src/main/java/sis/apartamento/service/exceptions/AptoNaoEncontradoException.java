package sis.apartamento.service.exceptions;

public class AptoNaoEncontradoException extends RuntimeException{

    public AptoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}