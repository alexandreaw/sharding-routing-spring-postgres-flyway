package com.spring.sharding.application.web.controller

import com.spring.sharding.application.web.api.ProductsApi
import com.spring.sharding.application.web.dto.ProductDto
import com.spring.sharding.domain.entity.Product
import com.spring.sharding.domain.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService
) : ProductsApi {

    override fun productsPost(productDto: ProductDto): ResponseEntity<ProductDto> {
        val product = productService.createProduct(productDto.toDomain())
        return ResponseEntity(product.toDto(), HttpStatus.CREATED)
    }

    override fun productsIdGet(id: String): ResponseEntity<ProductDto> {
        val product = productService.getProductById(id)
        return if (product != null) {
            ResponseEntity(product.toDto(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    override fun productsIdDelete(id: String): ResponseEntity<Void> {
        val success = productService.deleteProduct(id)
        return if (success) {
            ResponseEntity(HttpStatus.NO_CONTENT)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}

fun ProductDto.toDomain() = Product(
    name = name,
    price = price
)

fun Product.toDto() = ProductDto()
    .id(id)
    .name(name)
    .price(price)