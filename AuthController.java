package com.ecommerce.controller;

import com.ecommerce.dto.auth.LoginRequest;
import com.ecommerce.dto.auth.LoginResponse;
import com.ecommerce.entity.Utente;
import com.ecommerce.repository.UtenteRepository;
import com.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwt;
    private final UtenteRepository utenti;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
        Authentication auth = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        authManager.authenticate(auth);
        Utente u = utenti.findByUsername(req.getUsername()).orElseThrow();
        String ruolo = "admin".equalsIgnoreCase(u.getStato()) ? "ADMIN" : "USER";
        String token = jwt.generate(User.withUsername(u.getUsername()).password(u.getPassword()).authorities("ROLE_"+ruolo).build(), u.getId(), ruolo);
        return ResponseEntity.ok(new LoginResponse(token, u.getId(), u.getNome(), u.getCognome(), ruolo));
    }
}
