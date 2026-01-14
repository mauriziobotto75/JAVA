package com.guerra.service;

import com.guerra.model.Giocatore;
import org.springframework.stereotype.Service;

@Service
public class GiocoService {
    public void attendi(Giocatore utente) {
        utente.setPopolazione((int)(utente.getPopolazione() * 1.1));
        utente.setSoldati((int)(utente.getSoldati() * 1.1));
        utente.setSoldatiDisp((int)(utente.getSoldatiDisp() * 1.1));
        addDenaro(utente);
        addCibo(utente);
    }

    public void addestra(Giocatore utente) {
        utente.setSoldati((int)(utente.getSoldati() * 1.3));
        utente.setSoldatiDisp((int)(utente.getSoldatiDisp() * 1.3));
        addDenaro(utente);
        addCibo(utente);
    }

    public void incrementa(Giocatore utente) {
        utente.setPopolazione((int)(utente.getPopolazione() * 1.3));
        addDenaro(utente);
        addCibo(utente);
    }

    private void addDenaro(Giocatore utente) {
        utente.setDenaro((int)(utente.getDenaro() + (utente.getPopolazione() * 0.1) - (utente.getSoldati() * 0.1)));
    }

    private void addCibo(Giocatore utente) {
        utente.setCibo((int)(utente.getCibo() + (utente.getPopolazione() * 0.1) - (utente.getSoldati() * 0.1)));
    }
}
