package com.mateusz.zuk.currency.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD("usd");

    private final String currency;
}
