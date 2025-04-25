package com.mateusz.zuk.currency.client;

import com.mateusz.zuk.currency.exception.externalclient.NbpClientException;
import com.mateusz.zuk.currency.model.response.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import static com.mateusz.zuk.currency.exception.ErrorMessage.EXTERNAL_CLIENT_ERROR;
import static com.mateusz.zuk.currency.exception.ErrorMessage.EXTERNAL_SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class NbpClient {
    private final RestClient nbpClient;

    public CurrencyResponse getPlnExchangeRateByDate(String currency, LocalDate date) throws NbpClientException {
        return nbpClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/exchangerates/rates/A/{currency}/{date}")
                        .build(currency, date))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new NbpClientException(String.format(EXTERNAL_CLIENT_ERROR, response.getStatusText()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw new NbpClientException(String.format(EXTERNAL_SERVER_ERROR, response.getStatusText()));
                })
                .body(CurrencyResponse.class);
    }
}