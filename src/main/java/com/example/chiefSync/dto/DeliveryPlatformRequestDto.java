package com.example.chiefSync.dto;


import lombok.Data;

import java.util.List;

@Data
public class DeliveryPlatformRequestDto {
    private String orderIdFromPlatform;
    private String customAddress;
    private List<DeliveryItemDto> items;
}
