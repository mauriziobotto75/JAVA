package com.ecommerce.controller;

import com.ecommerce.dto.email.EmailRequest;
import com.ecommerce.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService service;
    @PostMapping("/contatta") public String invia(@RequestBody EmailRequest req){ service.invia(req); return "OK"; }
}
