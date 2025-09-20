package com.transaction.transaction.controller;


import com.transaction.transaction.entity.Order;
import com.transaction.transaction.service.OrderProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderProcessingController {

    private final OrderProcessingService orderProcessingService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody  Order order)
    {
        return new ResponseEntity<>(orderProcessingService.placeAndOrder(order), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public List<Order> all() { return orderProcessingService.findAll(); }

}
