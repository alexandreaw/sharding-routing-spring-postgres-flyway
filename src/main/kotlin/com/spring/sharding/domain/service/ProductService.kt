package com.spring.sharding.domain.service

import com.spring.sharding.domain.entity.Product
import com.spring.sharding.domain.gateway.ProductGateway
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productGateway: ProductGateway
) {
    // Method to get a product by ID
    fun getProductById(id: String): Product? {
        return productGateway.findById(id)
    }

    // Method to create a new product
    fun createProduct(product: Product): Product {
        productGateway.save(product)
        return product
    }

    // Method to delete a product
    fun deleteProduct(id: String): Boolean {
        return productGateway.deleteById(id)
    }
}
