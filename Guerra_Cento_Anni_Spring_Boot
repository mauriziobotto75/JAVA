package com.guerra.controller;

import com.guerra.model.Giocatore;
import com.guerra.service.GiocoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gioco")
public class GiocoController {

    private final GiocoService giocoService;

    public GiocoController(GiocoService giocoService) {
        this.giocoService = giocoService;
    }

    @PostMapping("/attendi")
    public Giocatore attendi(@RequestBody Giocatore utente) {
        giocoService.attendi(utente);
        return utente;
    }

    @PostMapping("/addestra")
    public Giocatore addestra(@RequestBody Giocatore utente) {
        giocoService.addestra(utente);
        return utente;
    }

    @PostMapping("/incrementa")
    public Giocatore incrementa(@RequestBody Giocatore utente) {
        giocoService.incrementa(utente);
        return utente;
    }
}
