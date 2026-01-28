package com.ecommerce.controller;

import com.ecommerce.dto.ordine.CheckoutRequest;
import com.ecommerce.entity.Ordine;
import com.ecommerce.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordini")
@RequiredArgsConstructor
public class OrdiniController {
    private final OrdineService service;

    @PostMapping("/checkout") public Ordine checkout(@RequestBody CheckoutRequest req){return service.checkout(req);}    
    @PostMapping("/salva-carrello") public Ordine salva(@RequestBody CheckoutRequest req){return service.salvaCarrello(req);}    
    @GetMapping("/utente/{id}") public List<Ordine> byUser(@PathVariable Integer id){return service.getByUtente(id);}    
}
