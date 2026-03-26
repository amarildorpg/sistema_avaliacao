package br.com.datanorte.sistemas_avaliacao.exception;

public class UsernameOrPasswordInvalidException extends RuntimeException {

    public UsernameOrPasswordInvalidException() {
        super("Usuário ou senha inválidos");
    }

    public UsernameOrPasswordInvalidException(String message) {
        super(message);
    }
}