package ru.zubcov.paymentstats.stats.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("Logging exception, status: {}, message: {}", e.getStatusCode(), e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(e.getStatusCode().toString(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
