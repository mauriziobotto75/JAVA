package com.example.erp.orders.service;

import com.example.erp.orders.dto.CreateOrderRequest;
import com.example.erp.orders.entity.Order;
import com.example.erp.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Order createOrder(CreateOrderRequest request) {

        Order order = new Order();

        order.setDocumentNo(request.documentNo());
        order.setDateOrdered(LocalDate.now());
        order.setGrandTotal(request.amount());
        order.setDocStatus("DR");

        return repository.save(order);
    }

    @Transactional
    public Order completeOrder(Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ordine non trovato"));

        order.setDocStatus("CO");

        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ordine non trovato"));
    }
}
