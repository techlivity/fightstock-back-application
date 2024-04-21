package br.com.fight.stock.app.exceptions;

public class PasswordFormatException extends RuntimeException {
    static final String MESSAGE = "The password must contain at least 8 characters, including at least 1 uppercase letter, 1 lowercase letter, 1 special character and 1 number.";
    public PasswordFormatException() {
        super(MESSAGE);
    }
}
