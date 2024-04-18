package br.com.fight.stock.app.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public abstract class ApiUtils {
    private static final String PATTERN = "dd/MM/yyyy HH:mm:ss";
    private static final String ZONE_ID = "America/Sao_Paulo";

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
}