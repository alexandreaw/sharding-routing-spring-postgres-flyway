package com.spring.sharding.domain.service

import com.spring.sharding.domain.entity.Product
import com.spring.sharding.domain.gateway.ProductGateway
import com.spring.sharding.domain.service.ProductService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ProductServiceTest {

    private lateinit var productService: ProductService
    private val productGateway: ProductGateway = mockk()

    @BeforeEach
    fun setup() {
        productService = ProductService(productGateway)
    }

    @Test
    fun `getProductById returns product`() {
        val product = Product(price = 10.0, name = "TV")
        val id = UUID.randomUUID().toString()
        every { productGateway.findById(id) } returns product

        val result = productService.getProductById(id)

        assertEquals(product, result)
    }

    @Test
    fun `createProduct saves and returns product`() {
        val product = Product(price = 10.0, name = "TV")
        every { productGateway.save(product) } returns true

        val result = productService.createProduct(product)

        verify { productGateway.save(product) }
        assertEquals(product, result)
    }

    @Test
    fun `deleteProduct deletes and returns true`() {
        val id = UUID.randomUUID().toString()
        every { productGateway.deleteById(id) } returns true

        val result = productService.deleteProduct(id)

        verify { productGateway.deleteById(id) }
        assertTrue(result)
    }
}