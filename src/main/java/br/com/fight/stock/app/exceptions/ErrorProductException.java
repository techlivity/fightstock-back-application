package br.com.fight.stock.app.exceptions;

public class ErrorProductException extends RuntimeException {
    public ErrorProductException(String message) {
        super(message);
    }
}
