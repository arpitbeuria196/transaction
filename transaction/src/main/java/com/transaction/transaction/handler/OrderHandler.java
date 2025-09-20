package com.transaction.transaction.handler;


import com.transaction.transaction.entity.Order;
import com.transaction.transaction.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandler {

    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order)
    {
       Order saved =  orderRepository.save(order);
       return  saved;
    }

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}
