package it.mauriziobotto.banca.web;

import it.mauriziobotto.banca.service.RegolaApplicativaException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RegolaApplicativaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String regolaApplicativa(RegolaApplicativaException exception, Model model) {
        model.addAttribute("messaggioErrore", exception.getMessage());
        return "errore";
    }
}
