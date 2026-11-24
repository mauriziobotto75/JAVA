package it.mauriziobotto.biblioteca.web;

import it.mauriziobotto.biblioteca.api.LibroResponse;
import it.mauriziobotto.biblioteca.api.PrestitoResponse;
import it.mauriziobotto.biblioteca.service.LibroService;
import it.mauriziobotto.biblioteca.service.PrestitoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final LibroService libri;
    private final PrestitoService prestiti;

    public ReportController(LibroService libri, PrestitoService prestiti) {
        this.libri = libri;
        this.prestiti = prestiti;
    }

    @GetMapping("/available-books")
    public List<LibroResponse> libriDisponibili() {
        return libri.elenco(true);
    }

    @GetMapping("/active-loans")
    public List<PrestitoResponse> prestitiAttivi() {
        return prestiti.elenco(true);
    }
}
