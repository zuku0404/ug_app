package com.mateusz.zuk.currency.model.dto.mapper;

import com.mateusz.zuk.currency.model.Order;
import com.mateusz.zuk.currency.model.dto.OrderDto;

import java.util.List;

public class OrderDtoMapper {
    private OrderDtoMapper() {
    }

    public static List<OrderDto> mapToOrderDtos(List<Order> orders) {
        return orders.stream()
                .map(OrderDtoMapper::mapToOrderDto)
                .toList();
    }

    private static OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getProductName(),
                order.getPostDate(),
                order.getCostUsd(),
                order.getCostPln());
    }
}
