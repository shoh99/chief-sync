package com.example.chiefSync.dto;

import com.example.chiefSync.model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private String menuName;
    private Integer quantity;
    private BigDecimal orderPrice;
}
