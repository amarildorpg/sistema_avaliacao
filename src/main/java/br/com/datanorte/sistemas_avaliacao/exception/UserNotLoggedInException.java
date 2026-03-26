package br.com.datanorte.sistemas_avaliacao.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super("Usuário não está logado");
    }
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
