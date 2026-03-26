package br.com.datanorte.sistemas_avaliacao.exeption;

public class UserInactiveException extends RuntimeException {
    public UserInactiveException(String message) {
        super(message);
    }
}
