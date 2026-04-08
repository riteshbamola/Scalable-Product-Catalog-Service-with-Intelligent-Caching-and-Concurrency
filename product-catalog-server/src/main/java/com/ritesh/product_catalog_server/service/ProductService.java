package com.ritesh.product_catalog_server.service;

import com.ritesh.product_catalog_server.db.ProductEntity;
import com.ritesh.product_catalog_server.db.ProductRepo;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepo productRepo;

    ProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }



    public ProductEntity getProduct(Long id){
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException("Product Not Found"));
    }

    public ProductEntity addProduct(ProductEntity product) {
        if (productRepo.existsById(product.getId())) {
            throw new RuntimeException(
                    "Product with ID " + product.getId() + " already exists."
            );
        }
        return productRepo.save(product);
    }



}
