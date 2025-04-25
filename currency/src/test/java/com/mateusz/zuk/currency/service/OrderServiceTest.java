package com.mateusz.zuk.currency.service;

import com.mateusz.zuk.currency.enums.SortField;
import com.mateusz.zuk.currency.model.Order;
import com.mateusz.zuk.currency.model.dto.OrderDto;
import com.mateusz.zuk.currency.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;


    @Test
    void shouldFindOrdersByCriteriaAndMapToDtos() {
        String productName = "Apple";
        LocalDate postDate = LocalDate.of(2024, 4, 1);
        SortField sortField = SortField.PRODUCT_NAME;
        Sort.Direction sortDirection = Sort.Direction.ASC;

        List<Order> mockOrders = List.of(
                new Order(1L, "Apple", postDate, null, null),
                new Order(2L, "Apple", postDate, null, null)
        );

        when(orderRepository.findOrdersByCriteria(eq(productName), eq(postDate), any(Sort.class)))
                .thenReturn(mockOrders);

        List<OrderDto> result = orderService.findOrdersByCriteria(productName, postDate, sortField, sortDirection);

        assertThat(result).hasSize(2);
        assertThat(result.getFirst().productName()).isEqualTo("Apple");
    }
}
