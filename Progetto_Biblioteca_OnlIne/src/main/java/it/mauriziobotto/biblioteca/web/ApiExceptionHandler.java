package it.mauriziobotto.biblioteca.web;

import it.mauriziobotto.biblioteca.service.PrestitoNonDisponibileException;
import it.mauriziobotto.biblioteca.service.RisorsaNonTrovataException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RisorsaNonTrovataException.class)
    public ResponseEntity<Map<String, String>> nonTrovata(RisorsaNonTrovataException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(PrestitoNonDisponibileException.class)
    public ResponseEntity<Map<String, String>> conflitto(PrestitoNonDisponibileException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> richiestaNonValida(MethodArgumentNotValidException exception) {
        String messaggio = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(errore -> errore.getField() + ": " + errore.getDefaultMessage())
                .orElse("Richiesta non valida.");
        return ResponseEntity.badRequest().body(Map.of("error", messaggio));
    }
}
