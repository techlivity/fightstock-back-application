package br.com.fight.stock.app.exceptions;

public class CategorieNotFoundException extends RuntimeException{
    public CategorieNotFoundException(String message) {
        super(message);
    }
}
