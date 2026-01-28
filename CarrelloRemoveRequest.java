package com.ecommerce.dto.carrello;

import lombok.Data;

@Data
public class CarrelloRemoveRequest {
    private Integer userId;
    private Integer prodottoId;
}
