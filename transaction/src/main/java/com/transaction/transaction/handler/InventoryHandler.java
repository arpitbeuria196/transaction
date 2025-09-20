package com.transaction.transaction.handler;


import com.transaction.transaction.entity.Product;
import com.transaction.transaction.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryHandler {

    private final InventoryRepository inventoryRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Product saveProduct(Product product)
    {
        if(product.getStockQuantity() < 5)
        {
            throw new RuntimeException("DB Crashed!");
        }
        return inventoryRepository.save(product);
    }

    public Product getProduct(int id)
    {
        return inventoryRepository.findById(id).orElseThrow(()->new RuntimeException("Product doesn't exist"));
    }
}
