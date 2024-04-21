package br.com.fight.stock.app.exceptions.handler;

import br.com.fight.stock.app.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@SuppressWarnings("rawtypes")
public class ResponseExceptionHandlerApi extends ResponseEntityExceptionHandler {

    static final String PATTERN_TIME_STAMP = "dd/MM/yyyy HH:mm:ss";

    @ExceptionHandler(value = {
            NotFoundCarouselException.class,
            LabelNotFoundException.class,
            ProductNotFoundException.class,
            CategorieNotFoundException.class,
            CepNotFoundException.class
    })
    public ResponseEntity<Err> handlerNotFoundCarouselException(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) {

        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.NOT_FOUND.value();
        var errorResponse = new ErrorResponse(exception.getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }

    @ExceptionHandler(value = {
            ExcessContactException.class,
            ErrorProductException.class
    })
    public ResponseEntity handlerExcessExceptions(HttpServletRequest request, HttpServletResponse response, RuntimeException exception) {
        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
        var errorResponse = new ErrorResponse(exception.getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handlerConstraintViolationException(HttpServletRequest request, HttpServletResponse response, ConstraintViolationException exception) {
        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.METHOD_NOT_ALLOWED.value();
        var errorResponse = new ErrorResponse(exception.getConstraintViolations()
                .iterator().next().getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }

    @ExceptionHandler(ErrorCallApiException.class)
    public ResponseEntity handlerApiError(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        var errorResponse = new ErrorResponse(exception.getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity handlerInvalidException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        var instant = Instant.now();
        var formatter = DateTimeFormatter.ofPattern(PATTERN_TIME_STAMP).withZone(ZoneId.systemDefault());
        var timeStamp = formatter.format(instant);
        var httpStatus = HttpStatus.UNAUTHORIZED.value();
        var errorResponse = new ErrorResponse(exception.getMessage(), timeStamp);
        return ResponseEntity.status(httpStatus).body(new Err(errorResponse));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.ok(errors);
    }

    record Err<T>(T error) {
    }

    record ErrorResponse(String message, String timeStamp) {
    }
}
