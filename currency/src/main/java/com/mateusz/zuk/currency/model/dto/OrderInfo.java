package com.mateusz.zuk.currency.model.dto;

import java.math.BigDecimal;

public record OrderInfo(
        String product,
        String productName,
        String postDate,
        BigDecimal costUsd,
        BigDecimal costPln) {
}
