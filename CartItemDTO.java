package com.ecommerce.dto.carrello;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Integer prodottoId;
    private Integer magazzinoId;
    private String nomeProdotto;
    private float prezzo;
    private int quantita;
}
