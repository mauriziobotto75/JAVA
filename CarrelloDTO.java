package com.ecommerce.dto.carrello;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class CarrelloDTO {
    private List<CartItemDTO> items = new ArrayList<>();
    private float totale = 0f;

    public void addItem(CartItemDTO i) {
        items.add(i);
        ricalcola();
    }
    public void removeItem(Integer prodottoId) {
        items.removeIf(x -> x.getProdottoId().equals(prodottoId));
        ricalcola();
    }
    public void ricalcola() {
        totale = 0f;
        for (CartItemDTO i : items) totale += i.getPrezzo() * i.getQuantita();
    }
}
