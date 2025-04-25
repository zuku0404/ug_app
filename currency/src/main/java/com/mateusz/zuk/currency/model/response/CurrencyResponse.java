package com.mateusz.zuk.currency.model.response;

import java.util.List;

public record CurrencyResponse(
        List<Rate> rates) {}
