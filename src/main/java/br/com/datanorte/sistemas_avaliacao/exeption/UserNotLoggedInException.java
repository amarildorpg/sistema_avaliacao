package br.com.datanorte.sistemas_avaliacao.exeption;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message) {
        super(message);
    }

}
