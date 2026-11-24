package it.mauriziobotto.biblioteca.web;

import it.mauriziobotto.biblioteca.api.LettoreRequest;
import it.mauriziobotto.biblioteca.api.LettoreResponse;
import it.mauriziobotto.biblioteca.service.LettoreService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/readers")
public class LettoriController {

    private final LettoreService lettori;

    public LettoriController(LettoreService lettori) {
        this.lettori = lettori;
    }

    @GetMapping
    public List<LettoreResponse> elenco() {
        return lettori.elenco();
    }

    @GetMapping("/{id}")
    public LettoreResponse trova(@PathVariable Long id) {
        return lettori.trova(id);
    }

    @PostMapping
    public ResponseEntity<LettoreResponse> crea(@Valid @RequestBody LettoreRequest richiesta) {
        LettoreResponse lettore = lettori.crea(richiesta);
        return ResponseEntity.created(URI.create("/api/readers/" + lettore.id())).body(lettore);
    }
}
