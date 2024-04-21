package br.com.fight.stock.app.utils;

import br.com.fight.stock.app.exceptions.PasswordFormatException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public abstract class ApiUtils {
    private static final String PATTERN = "dd/MM/yyyy HH:mm:ss";
    private static final String ZONE_ID = "America/Sao_Paulo";
    private static final String REGEX_PASSWORD_FORMAT = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private ApiUtils() {
    }

    public static String formatMessage(String message, Object... variables) {
        return String.format(message, variables);
    }

    public static String convertInstantToLocalDateTime(Instant time) {
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.of(ZONE_ID));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(PATTERN));
    }

    public static String validatePassword(String password) {
        if (!Pattern.matches(REGEX_PASSWORD_FORMAT, password)) {
            throw new PasswordFormatException();
        }
        return password;
    }
}