package br.com.fight.stock.app.exceptions;

public class LabelNotFoundException extends RuntimeException{
    public LabelNotFoundException(String message) {
        super(message);
    }
}
