package it.mauriziobotto.biblioteca.web;

import it.mauriziobotto.biblioteca.api.PrestitoRequest;
import it.mauriziobotto.biblioteca.api.PrestitoResponse;
import it.mauriziobotto.biblioteca.service.PrestitoService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class PrestitiController {

    private final PrestitoService prestiti;

    public PrestitiController(PrestitoService prestiti) {
        this.prestiti = prestiti;
    }

    @GetMapping
    public List<PrestitoResponse> elenco(@RequestParam(defaultValue = "false") boolean activeOnly) {
        return prestiti.elenco(activeOnly);
    }

    @PostMapping
    public ResponseEntity<PrestitoResponse> crea(@Valid @RequestBody PrestitoRequest richiesta) {
        PrestitoResponse prestito = prestiti.crea(richiesta);
        return ResponseEntity.created(URI.create("/api/loans/" + prestito.id())).body(prestito);
    }

    @PostMapping("/{id}/return")
    public PrestitoResponse restituisci(@PathVariable Long id) {
        return prestiti.registraRestituzione(id);
    }
}
