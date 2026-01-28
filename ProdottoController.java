package com.ecommerce.controller;

import com.ecommerce.entity.Prodotto;
import com.ecommerce.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prodotti")
@RequiredArgsConstructor
public class ProdottoController {
    private final ProdottoService service;

    @GetMapping public List<Prodotto> list(){return service.listAll();}
    @GetMapping("/{id}") public Prodotto get(@PathVariable Integer id){return service.getById(id);}    
    @GetMapping("/search") public List<Prodotto> search(@RequestParam String q){return service.search(q);}    
    @PostMapping public Prodotto create(@RequestBody Prodotto p){return service.create(p);}    
    @PutMapping("/{id}") public Prodotto update(@PathVariable Integer id, @RequestBody Prodotto p){return service.update(id, p);}    
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){service.delete(id);}    
}
