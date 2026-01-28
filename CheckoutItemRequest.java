package com.ecommerce.dto.ordine;

import lombok.Data;

@Data
public class CheckoutItemRequest {
    private Integer prodottoId;
    private Integer magazzinoId;
    private int quantita;
}
