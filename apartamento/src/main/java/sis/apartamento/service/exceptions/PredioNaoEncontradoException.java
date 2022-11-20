package sis.apartamento.service.exceptions;

public class PredioNaoEncontradoException extends RuntimeException{
    public PredioNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
