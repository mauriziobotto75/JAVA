package com.example.erp.orders.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "c_order")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_order_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String documentNo;

    private LocalDate dateOrdered;

    private BigDecimal grandTotal;

    private String docStatus;
}
