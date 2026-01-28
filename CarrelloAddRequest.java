package com.ecommerce.dto.carrello;

import lombok.Data;

@Data
public class CarrelloAddRequest {
    private Integer userId;
    private CartItemDTO item;
}
