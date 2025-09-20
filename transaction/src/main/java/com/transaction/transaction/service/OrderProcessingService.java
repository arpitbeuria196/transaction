package com.transaction.transaction.service;

import com.transaction.transaction.entity.Order;
import com.transaction.transaction.entity.Product;
import com.transaction.transaction.handler.AuditHandler;
import com.transaction.transaction.handler.InventoryHandler;
import com.transaction.transaction.handler.OrderHandler;
import com.transaction.transaction.handler.paymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {
    private final paymentValidator paymentValidator;
    private final OrderHandler orderHandler;
    private final InventoryHandler inventoryHandler;
    private final AuditHandler auditHandler;


    //REQUIRED: join an existing transaction or create a new one
    //REQUIRES_NEW: Always create a new transaction, suspending if any transaction exists
    //MANDATORY: Require an existing transaction,If nothing found it will throw exception
    //NEVER: Ensure the method will run with transaction,throw an exception if found any
    //NotSupported:Execute method without transacion,suspending any active transaction

    //isolation
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
        Order saved = null;

        try
        {
             saved = orderHandler.saveOrder(order);

            int availableStock = product.getStockQuantity() - order.getQuantity();

            product.setStockQuantity(availableStock);
            inventoryHandler.saveProduct(product);

            //required new propagation
            auditHandler.auditOrderDetails(order,"Order Placement Succeeded");
        } catch (Exception e) {
            auditHandler.auditOrderDetails(order,"Order Placement Failed");
        }

        //vaidatePayment()
        paymentValidator.validatePayment(order);



        return saved;
    }



    public List<Order> findAll() {

        List<Order> orders = orderHandler.getAllOrders();

        return  orders;
    }
}
