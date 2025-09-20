package com.transaction.transaction.controller;

import com.transaction.transaction.entity.Product;
import com.transaction.transaction.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductCreateController {

    private final InventoryRepository inventoryRepository;

    @PostMapping
    public String createProduct(@RequestBody Product product)
    {
       Optional<Product> product1 =  inventoryRepository.findById(product.getId());
       if(product1.isEmpty())
       {
           inventoryRepository.save(product);
           return "Product Created!";
       }
       throw new RuntimeException("Product Already Exists!") ;
    }

    @GetMapping
    public List<Product> getAllProducts()
    {
        return inventoryRepository.findAll();
    }
}
