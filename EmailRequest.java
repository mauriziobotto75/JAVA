package com.ecommerce.dto.email;

import lombok.Data;

@Data
public class EmailRequest {
    private String nome;
    private String cognome;
    private String email;
    private String messaggio;
}
