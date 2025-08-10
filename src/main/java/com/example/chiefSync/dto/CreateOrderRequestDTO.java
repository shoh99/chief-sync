package com.example.chiefSync.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateOrderRequestDTO {
    private Long menuItemId;
    private Integer quantity;
}
