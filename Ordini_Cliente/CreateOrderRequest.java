package com.example.erp.orders.dto;

import java.math.BigDecimal;

public record CreateOrderRequest(
        String documentNo,
        BigDecimal amount
) {
}
