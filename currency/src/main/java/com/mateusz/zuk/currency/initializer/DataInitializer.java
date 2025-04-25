package com.mateusz.zuk.currency.initializer;

import com.mateusz.zuk.currency.enums.Currency;
import com.mateusz.zuk.currency.client.NbpClient;
import com.mateusz.zuk.currency.exception.externalclient.NbpClientException;
import com.mateusz.zuk.currency.exception.loader.DataLoadingException;
import com.mateusz.zuk.currency.exception.report.ReportGenerationException;
import com.mateusz.zuk.currency.model.Order;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import com.mateusz.zuk.currency.model.dto.OrderInput;
import com.mateusz.zuk.currency.loader.DataLoader;
import com.mateusz.zuk.currency.report.InvoiceReportGenerator;
import com.mateusz.zuk.currency.model.response.CurrencyResponse;
import com.mateusz.zuk.currency.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final NbpClient nbpClient;
    private final OrderRepository orderRepository;
    private final DataLoader dataLoader;
    private final @Qualifier("xmlInvoiceReportGenerator") InvoiceReportGenerator generator;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Transactional
    @Override
    public void run(String... args) {
        try {
            List<OrderInput> orderInputs = dataLoader.loadData();
            Set<LocalDate> uniqueDates = orderInputs.stream()
                    .map(OrderInput::postDate)
                    .collect(Collectors.toSet());
            Map<LocalDate, BigDecimal> currencyRates = findCurrencyForDates(uniqueDates);

            List<Order> orders = new ArrayList<>();
            List<OrderInfo> orderInfos = new ArrayList<>();

            orderInputs.forEach(orderInput -> {
                String product = orderInput.product();
                String productName = orderInput.productName();
                LocalDate postDate = orderInput.postDate();
                BigDecimal cost = orderInput.costUsd();
                BigDecimal exchangeRate = currencyRates.get(postDate);
                BigDecimal productCostPln = calculatePriceFrom(exchangeRate, cost);

                OrderInfo orderInfo = new OrderInfo(product, productName, postDate.toString(), cost, productCostPln);
                orderInfos.add(orderInfo);

                Order order = Order.builder()
                        .productName(productName)
                        .postDate(postDate)
                        .costUsd(cost)
                        .costPln(productCostPln)
                        .build();
                orders.add(order);
            });
            orderRepository.saveAll(orders);
            generator.generateFile(orderInfos);
        } catch (DataLoadingException e) {
            logger.error("Error loading data: {}", e.getMessage(), e);
        } catch (ReportGenerationException e) {
            logger.error("Error generating report: {}", e.getMessage(), e);
        }
    }

    private Map<LocalDate, BigDecimal> findCurrencyForDates(Set<LocalDate> uniqueDates) {
        Map<LocalDate, BigDecimal> currencyRates = new HashMap<>();
        uniqueDates.forEach(postDate -> {
            try {
                CurrencyResponse currencyResponse = nbpClient.getPlnExchangeRateByDate(Currency.USD.getCurrency(), postDate);
                BigDecimal plnExchangeRateByDate = currencyResponse.rates().getFirst().mid();
                currencyRates.put(postDate, plnExchangeRateByDate);
            } catch (NbpClientException e) {
                logger.error("Problem with reading data from an external source: {}", e.getMessage());
                currencyRates.put(postDate, null);
            }
        });
        return currencyRates;
    }


    private BigDecimal calculatePriceFrom(BigDecimal plnExchangeRate, BigDecimal costUsd) {
        return plnExchangeRate == null ? null : costUsd.multiply(plnExchangeRate).setScale(2, RoundingMode.HALF_EVEN);
    }
}
