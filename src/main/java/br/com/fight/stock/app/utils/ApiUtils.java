package br.com.fight.stock.app.utils;

public abstract class ApiUtils {

    private ApiUtils() {
    }

    public static String formatMessage(String message, Object... variables) {
        return String.format(message, variables);
    }
}