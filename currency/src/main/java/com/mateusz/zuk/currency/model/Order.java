package com.mateusz.zuk.currency.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builder")
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "post_date")
    private LocalDate postDate;
    @Column(name = "cost_usd")
    private BigDecimal costUsd;
    @Column(name = "cost_pln")
    private BigDecimal costPln;
}
