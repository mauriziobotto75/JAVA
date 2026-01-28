package com.ecommerce.controller;

import com.ecommerce.dto.carrello.*;
import com.ecommerce.service.CarrelloService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrello")
@RequiredArgsConstructor
public class CarrelloController {
    private final CarrelloService service;

    @GetMapping("/{userId}") public CarrelloDTO get(@PathVariable Integer userId){return service.get(userId);}    
    @PostMapping("/aggiungi") public CarrelloDTO add(@RequestBody CarrelloAddRequest req){return service.aggiungi(req);}    
    @PostMapping("/rimuovi") public CarrelloDTO del(@RequestBody CarrelloRemoveRequest req){return service.rimuovi(req);}    
}
