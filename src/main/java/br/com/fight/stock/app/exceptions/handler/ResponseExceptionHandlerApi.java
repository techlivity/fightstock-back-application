package br.com.fight.stock.app.exceptions.handler;

import br.com.fight.stock.app.exceptions.LabelNotFoundException;
import br.com.fight.stock.app.exceptions.NotFoundCarouselException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class ResponseExceptionHandlerApi extends ResponseEntityExceptionHandler {

    static final String PATTERN_TIME_STAMP = "dd/MM/yyyy HH:mm:ss";

    @ExceptionHandler(value = {NotFoundCarouselException.class, LabelNotFoundException.class})
    public ResponseEntity<Err> handlerNotFoundCarouselException(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) {

        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.NOT_FOUND.value();
        var errorResponse = new ErrorResponse(exception.getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }


    record Err(ErrorResponse response) {
    }

    record ErrorResponse(String message, String timeStamp) {
    }
}
