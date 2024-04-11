package br.com.fight.stock.app.exceptions;

public class NotFoundCategoryException extends RuntimeException {
    public NotFoundCategoryException(String message) {
        super(message);
    }
}
