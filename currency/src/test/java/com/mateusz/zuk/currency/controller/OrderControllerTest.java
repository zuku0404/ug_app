package com.mateusz.zuk.currency.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusz.zuk.currency.enums.SortField;
import com.mateusz.zuk.currency.model.dto.OrderDto;
import com.mateusz.zuk.currency.service.OrderService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    private List<OrderDto> orders = Arrays.asList(
            new OrderDto(1L, "ausu", LocalDate.of(2023, 1, 21), new BigDecimal(101), new BigDecimal(119)),
            new OrderDto(2L, "dell", LocalDate.of(2013, 2, 1), new BigDecimal(211), new BigDecimal(434)),
            new OrderDto(3L, "hp", LocalDate.of(2015, 11, 12), new BigDecimal(12), new BigDecimal(13)));

    @Test
    void getAllOrders_ShouldReturnAllOrders() throws Exception {
        given(orderService.findOrdersByCriteria(null, null, SortField.ID, Sort.Direction.ASC)).willReturn(orders);
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/orders")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        String expectedJson = objectMapper.writeValueAsString(orders);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        JSONAssert.assertEquals(expectedJson, response.getContentAsString(), JSONCompareMode.LENIENT);
    }

    @Test
    void getAllOrders_ShouldReturnError() throws Exception {
        given(orderService.findOrdersByCriteria(null, null, SortField.ID, Sort.Direction.ASC)).willThrow(new RuntimeException("XXXXX"));
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/orders")
                                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
