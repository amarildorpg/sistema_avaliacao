package br.com.datanorte.sistemas_avaliacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationConfigAdvice {

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUsernameOrPasswordInvalidException(UsernameOrPasswordInvalidException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserInactiveException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUserInactiveException(UserInactiveException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleUserNotLoggedInException(UserNotLoggedInException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
        return new ErrorResponse("Erro interno no servidor");
    }


}
