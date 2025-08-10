package com.example.chiefSync.service;

import com.example.chiefSync.dto.*;
import com.example.chiefSync.model.*;
import com.example.chiefSync.repostiroy.MenuItemRepository;
import com.example.chiefSync.repostiroy.OrderItemRepository;
import com.example.chiefSync.repostiroy.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public OrderResponseDto createOrder(CreateOrderRequestsDTO orderRequestsDTO) {
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.RECEIVED);
        newOrder.setSource(orderRequestsDTO.getSource());

        for (CreateOrderRequestDTO itemDto : orderRequestsDTO.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemDto.getMenuItemId())
                    .orElseThrow(() -> new RuntimeException("Menu Item not found with id: " + itemDto.getMenuItemId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());

            newOrder.getOrderItems().add(orderItem);
        }
         return mapOrderToResponseDto(orderRepository.save(newOrder));

    }

    @Transactional
    public OrderResponseDto createDeliveryPlatformOrder(DeliveryPlatformRequestDto requestDto) {
        Order newOrder = new Order();
        newOrder.setStatus(OrderStatus.RECEIVED);
        newOrder.setSource(OrderSource.DELIVERY_PLATFORM);

        for (DeliveryItemDto itemDto: requestDto.getItems()) {
            MenuItem menuItem = menuItemRepository.findMenuItemByName(itemDto.getMenuItemName())
                    .orElseThrow(() -> new RuntimeException("Invalid menu item name from platform: " + itemDto.getMenuItemName()));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(newOrder);
            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(itemDto.getQuantity());

            newOrder.getOrderItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(newOrder);
        return mapOrderToResponseDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order foundOrder = orderRepository.findById(orderId).orElseThrow(() ->new RuntimeException("Order not found with id: " + orderId));
        foundOrder.setStatus(newStatus);
        OrderResponseDto response = mapOrderToResponseDto(orderRepository.save(foundOrder));

        System.out.println("Sending message to websocket");
        messagingTemplate.convertAndSend("/topic/orders", response);
        return response;
    }




    private OrderResponseDto mapOrderToResponseDto(Order newOrder) {
        OrderResponseDto response = new OrderResponseDto();
        response.setResponseOrderDtoList(new ArrayList<>());
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem singleOrder: newOrder.getOrderItems()) {
            OrderItemResponseDto orderDto = new OrderItemResponseDto();

            MenuItem menuItem = singleOrder.getMenuItem();
            orderDto.setMenuName(menuItem.getName());
            orderDto.setQuantity(singleOrder.getQuantity());

            BigDecimal orderPrice = menuItem.getPrice().multiply(BigDecimal.valueOf(singleOrder.getQuantity()));
            orderDto.setOrderPrice(orderPrice);

            totalPrice = totalPrice.add(orderPrice);

            response.getResponseOrderDtoList().add(orderDto);
        }
        response.setOrderId(newOrder.getId());
        response.setTotalPrice(totalPrice);
        response.setStatus(newOrder.getStatus());
        response.setSource(newOrder.getSource());

        return response;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


}
