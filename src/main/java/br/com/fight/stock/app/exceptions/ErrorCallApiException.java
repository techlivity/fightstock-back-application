package br.com.fight.stock.app.exceptions;

public class ErrorCallApiException extends RuntimeException{

    public ErrorCallApiException(String message) {
        super(message);
    }
}
