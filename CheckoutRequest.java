package com.ecommerce.dto.ordine;

import lombok.Data;
import java.util.List;

@Data
public class CheckoutRequest {
    private Integer userId;
    private List<CheckoutItemRequest> items;
}
