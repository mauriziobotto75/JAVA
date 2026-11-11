package it.mauriziobotto.assicurazioni.web;

import it.mauriziobotto.assicurazioni.polizze.PolizzaNonTrovataException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PolizzaExceptionHandler {

    @ExceptionHandler(PolizzaNonTrovataException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String polizzaNonTrovata(PolizzaNonTrovataException exception, Model model) {
        model.addAttribute("messaggio", exception.getMessage());
        return "errore/404";
    }
}
