package it.mauriziobotto.biblioteca.web;

import it.mauriziobotto.biblioteca.api.LibroRequest;
import it.mauriziobotto.biblioteca.api.LibroResponse;
import it.mauriziobotto.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class LibriController {

    private final LibroService libri;

    public LibriController(LibroService libri) {
        this.libri = libri;
    }

    @GetMapping
    public List<LibroResponse> elenco(@RequestParam(defaultValue = "false") boolean availableOnly) {
        return libri.elenco(availableOnly);
    }

    @PostMapping
    public ResponseEntity<LibroResponse> crea(@Valid @RequestBody LibroRequest richiesta) {
        LibroResponse libro = libri.crea(richiesta);
        return ResponseEntity.created(URI.create("/api/books/" + libro.id())).body(libro);
    }
}
