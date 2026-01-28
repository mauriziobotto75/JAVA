package com.ecommerce.dto.prodotto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdottoDTO {
    private Integer id;
    private String nome;
    private String descrizione;
    private float prezzo;
    private String immagine;
}
