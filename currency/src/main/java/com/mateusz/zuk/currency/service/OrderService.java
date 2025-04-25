package com.mateusz.zuk.currency.service;

import com.mateusz.zuk.currency.enums.SortField;
import com.mateusz.zuk.currency.model.Order;
import com.mateusz.zuk.currency.model.dto.OrderDto;
import com.mateusz.zuk.currency.model.dto.mapper.OrderDtoMapper;
import com.mateusz.zuk.currency.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;

    public List<OrderDto> findOrdersByCriteria(String name, LocalDate postDate, SortField sortField, Sort.Direction sortDirection) {
        Sort sortable = createSort(sortField, sortDirection);
        List<Order> orders = orderRepository.findOrdersByCriteria(name, postDate, sortable);
        return OrderDtoMapper.mapToOrderDtos(orders);
    }

    private Sort createSort(SortField sortField, Sort.Direction sortDirection) {
        Sort sorted = switch (sortField) {
            case PRODUCT_NAME -> Sort.by("productName");
            case POST_DATE -> Sort.by("postDate");
            default -> Sort.by("id");
        };
        return sortDirection == Sort.Direction.DESC ? sorted.descending() : sorted.ascending();
    }
}
