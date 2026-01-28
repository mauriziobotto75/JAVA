package com.ecommerce.controller;

import com.ecommerce.entity.Prodotto;
import com.ecommerce.service.ProdottoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/prodotti")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProdottoController {
    private final ProdottoService service;
    @PostMapping public Prodotto create(@RequestBody Prodotto p){return service.create(p);}    
    @PutMapping("/{id}") public Prodotto update(@PathVariable Integer id, @RequestBody Prodotto p){return service.update(id, p);}    
    @DeleteMapping("/{id}") public void delete(@PathVariable Integer id){service.delete(id);}    
}
