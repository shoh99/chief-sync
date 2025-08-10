package com.example.chiefSync.dto;

import com.example.chiefSync.model.OrderSource;
import com.example.chiefSync.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private List<OrderItemResponseDto> responseOrderDtoList;
    private BigDecimal totalPrice;
    private OrderStatus status;
    private OrderSource source;
}
