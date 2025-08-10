package com.example.chiefSync.controller;

import com.example.chiefSync.dto.CreateOrderRequestsDTO;
import com.example.chiefSync.dto.OrderResponseDto;
import com.example.chiefSync.dto.UpdateOrderStatusDTO;
import com.example.chiefSync.model.Order;
import com.example.chiefSync.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusDTO dto
    ) {
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, dto.getStatus());
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<OrderResponseDto> createNewOrder(@RequestBody CreateOrderRequestsDTO orderRequestsDTO) {
        OrderResponseDto newOrder = orderService.createOrder(orderRequestsDTO);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

}
