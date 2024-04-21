package br.com.fight.stock.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "senha invalida")
public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String s) {
        super(s);
    }
}