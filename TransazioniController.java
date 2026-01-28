package com.ecommerce.controller;

import com.ecommerce.entity.MagazzinoOut;
import com.ecommerce.service.MagazzinoOutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transazioni")
@RequiredArgsConstructor
public class TransazioniController {
    private final MagazzinoOutService service;

    @GetMapping public List<MagazzinoOut> list(){return service.listAll();}
}
