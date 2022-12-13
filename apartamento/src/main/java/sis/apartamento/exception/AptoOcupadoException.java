package sis.apartamento.exception;

public class AptoOcupadoException extends EntidadeEmUsoException{
    public AptoOcupadoException(String mensagem) {
        super(mensagem);
    }

    public AptoOcupadoException(Long idApartamento) {
        this(String.format("Apartamento Ocupado no codigo %d", idApartamento));
    }
}
