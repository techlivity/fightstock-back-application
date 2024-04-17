package br.com.fight.stock.app.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ApiUtils {

    private ApiUtils() {
    }

    public static String formatMessage(String message, Object... variables) {
        return String.format(message, variables);
    }

    public static String convertInstantToLocalDateTime(Instant time) {
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of("America/Sao_Paulo"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}