package com.ecommerce.controller;

import com.ecommerce.dto.magazzino.MagazzinoInRequest;
import com.ecommerce.dto.magazzino.UpdateQtyRequest;
import com.ecommerce.entity.MagazzinoIn;
import com.ecommerce.service.MagazzinoInService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/magazzino")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MagazzinoInController {
    private final MagazzinoInService service;

    @GetMapping public List<MagazzinoIn> list(){return service.listAll();}
    @PostMapping("/aggiungi") public MagazzinoIn add(@RequestBody MagazzinoInRequest req){return service.add(req);}    
    @PutMapping("/{id}/quantita") public MagazzinoIn upd(@PathVariable Integer id, @RequestBody UpdateQtyRequest req){return service.updateQuantita(id, req);}    
    @DeleteMapping("/{id}") public void del(@PathVariable Integer id){service.delete(id);}    
}
