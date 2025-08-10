package com.example.chiefSync.controller;

import com.example.chiefSync.dto.DeliveryPlatformRequestDto;
import com.example.chiefSync.dto.OrderResponseDto;
import com.example.chiefSync.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/external/delivery")
public class DeliveryPlatformController {

    private final OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> receiveNewOrder(@RequestBody DeliveryPlatformRequestDto requestDto) {
        OrderResponseDto newOrder = orderService.createDeliveryPlatformOrder(requestDto);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }
}
