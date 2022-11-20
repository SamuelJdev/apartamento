package sis.apartamento.service.exceptions;

public class LocacaoNaoEncontradoException extends RuntimeException{
    public LocacaoNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
