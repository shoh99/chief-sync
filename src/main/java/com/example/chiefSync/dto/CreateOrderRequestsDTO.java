package com.example.chiefSync.dto;

import com.example.chiefSync.model.OrderSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderRequestsDTO {
   List<CreateOrderRequestDTO> items;
   private OrderSource source;
}
