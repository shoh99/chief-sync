package com.example.chiefSync.dto;

import com.example.chiefSync.model.OrderStatus;
import lombok.Data;

@Data
public class UpdateOrderStatusDTO {
    private OrderStatus status;
}
