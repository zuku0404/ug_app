package com.mateusz.zuk.currency.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderDto(
        Long id,
        String productName,
        LocalDate postDate,
        BigDecimal costUsd,
        BigDecimal costPln
) {
}
