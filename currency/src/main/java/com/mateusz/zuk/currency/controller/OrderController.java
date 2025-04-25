package com.mateusz.zuk.currency.controller;

import com.mateusz.zuk.currency.enums.SortField;
import com.mateusz.zuk.currency.model.dto.OrderDto;
import com.mateusz.zuk.currency.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(
            @RequestParam(name = "productName", required = false) String name,
            @RequestParam(name = "postDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(name = "sortField", required = false, defaultValue = "ID") SortField sortField,
            @RequestParam(name = "sortDirection", required = false, defaultValue = "ASC") Sort.Direction sortDirection) {
        List<OrderDto> orders = orderService.findOrdersByCriteria(name, date, sortField, sortDirection);
        return ResponseEntity.ok(orders);
    }
}
