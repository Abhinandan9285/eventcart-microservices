package order_service.controller;

import common_lib.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import order_service.dto.request.ContactInfoDto;
import order_service.dto.response.OrderDetailsResponse;
import order_service.dto.request.CreateOrderRequest;
import order_service.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ContactInfoDto contactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping("/details/{orderId}")
    public OrderDetailsResponse getOrderDetails(@PathVariable("orderId") UUID orderId) {
        return orderService.getOrderDetails(orderId);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> contactInfo(){
        return ResponseEntity.ok(contactInfoDto);
    }
}