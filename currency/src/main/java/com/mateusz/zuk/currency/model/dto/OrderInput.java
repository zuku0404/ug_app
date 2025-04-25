package com.mateusz.zuk.currency.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderInput(
        String product,
        String productName,
        LocalDate postDate,
        BigDecimal costUsd) {
}
