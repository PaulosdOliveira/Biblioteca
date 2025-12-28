package com.example.biblioteca.application.ex;

import com.example.biblioteca.application.ex.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {


    @ExceptionHandler(EmailDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerEmailDuplicadoException(EmailDuplicadoException e) {
        return e.getMessage();
    }


    @ExceptionHandler(ClientrDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handlerClientrDuplicadoException(ClientrDuplicadoException e) {
        return e.getMessage();
    }


    @ExceptionHandler(CodigoExpiradoException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String handlerCodigoExpiradoException(CodigoExpiradoException e){
        return e.getMessage();
    }


    @ExceptionHandler(CodigoInvalidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerCodigoInvalidoException(CodigoInvalidoException e){
        return e.getMessage();
    }


    @ExceptionHandler(EmailNaoValidadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handlerEmailNaoValidadoException(EmailNaoValidadoException e){
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerRuntimeException(RuntimeException e) {
        System.out.println(e);
        return "Ocorreu um erro inesperado";
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerUsernameNotFoundException(UsernameNotFoundException e) {
        System.out.println(e);
        return "Usuário não encontrado";
    }
}
