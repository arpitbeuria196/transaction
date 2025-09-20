package com.transaction.transaction.service;

import com.transaction.transaction.entity.Order;
import com.transaction.transaction.entity.Product;
import com.transaction.transaction.handler.InventoryHandler;
import com.transaction.transaction.handler.OrderHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {

    private final OrderHandler orderHandler;
    private final InventoryHandler inventoryHandler;

    //REQUIRED: join an existing transaction or create a new one
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public Order placeAndOrder(Order order)
    {
        Product product = inventoryHandler.getProduct(order.getProductId());

        if(product.getStockQuantity()< order.getQuantity())
        {
            throw new RuntimeException("Stock is Not available");
        }

        double price = order.getQuantity() * product.getPrice();

        order.setTotalPrice(price);

        Order saved = orderHandler.saveOrder(order);

        int availableStock = product.getStockQuantity() - order.getQuantity();

        product.setStockQuantity(availableStock);
        inventoryHandler.saveProduct(product);
        return saved;
    }



    public List<Order> findAll() {

        List<Order> orders = orderHandler.getAllOrders();

        return  orders;
    }
}
