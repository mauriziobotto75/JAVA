package com.example.erp.orders.controller;

import com.example.erp.orders.dto.CreateOrderRequest;
import com.example.erp.orders.entity.Order;
import com.example.erp.orders.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(
            @RequestBody CreateOrderRequest request) {

        return service.createOrder(request);
    }

    @GetMapping("/{id}")
    public Order findById(
            @PathVariable Long id) {

        return service.getOrder(id);
    }

    @PutMapping("/{id}/complete")
    public Order completeOrder(
            @PathVariable Long id) {

        return service.completeOrder(id);
    }
}
