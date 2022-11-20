package sis.apartamento.service.exceptions;

public class BussinesException extends RuntimeException{
    public BussinesException(String mensagem){
        super(mensagem);
    }
}
