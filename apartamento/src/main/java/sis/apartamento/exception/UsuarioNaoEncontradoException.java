package sis.apartamento.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{
    public UsuarioNaoEncontradoException(String messagem) {
        super(messagem);
    }
    public UsuarioNaoEncontradoException(Long idUsuario) {
        this(String.format("Não existem cadastro de Usuario com o codigo %d", idUsuario));
    }
}
