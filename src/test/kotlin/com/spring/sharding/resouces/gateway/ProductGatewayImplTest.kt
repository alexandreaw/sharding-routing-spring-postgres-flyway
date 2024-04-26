package com.spring.sharding.resouces.gateway

import com.spring.sharding.domain.entity.Product
import com.spring.sharding.application.config.ShardingSelector
import com.spring.sharding.resouces.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ProductGatewayImplTest {

    private lateinit var productGatewayImpl: ProductGatewayImpl
    private val productRepository: ProductRepository = mockk()
    private val shardingSelector: ShardingSelector = mockk()
    private val shardingSlot = slot<() -> Any>()

    @BeforeEach
    fun setup() {
        productGatewayImpl = ProductGatewayImpl(productRepository, shardingSelector)
    }

    @Test
    fun `findById returns product when product exists`() {
        val product = Product(price = 10.0, name = "TV")
        val id = UUID.randomUUID().toString()
        every { shardingSelector.selectSharding(id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.findById(id) } answers {
            product
        }

        val result = productGatewayImpl.findById(id)

        assertEquals(product, result)
    }

    @Test
    fun `findById returns null when product does not exist`() {
        val id = UUID.randomUUID().toString()
        every { shardingSelector.selectSharding(id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.findById(id) } answers {
            null
        }
        val result = productGatewayImpl.findById(id)

        assertNull(result)
    }

    @Test
    fun `save returns true when product is saved successfully`() {
        val product = Product(price = 10.0, name = "TV")
        every { shardingSelector.selectSharding(product.id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.save(product) } answers {
            1
        }
        val result = productGatewayImpl.save(product)

        assertTrue(result)
    }

    @Test
    fun `save returns false when product save fails`() {
        val product = Product(price = 10.0, name = "TV")
        every { shardingSelector.selectSharding(product.id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.save(product) } answers {
            0
        }

        val result = productGatewayImpl.save(product)

        assertFalse(result)
    }

    @Test
    fun `deleteById returns true when product is deleted successfully`() {
        val id = UUID.randomUUID().toString()
        every { shardingSelector.selectSharding(id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.delete(id) } answers {
            1
        }

        val result = productGatewayImpl.deleteById(id)

        assertTrue(result)
    }

    @Test
    fun `deleteById returns false when product deletion fails`() {
        val id = UUID.randomUUID().toString()
        every { shardingSelector.selectSharding(id, capture(shardingSlot)) } answers {
            shardingSlot.captured.invoke()
        }
        every { productRepository.delete(id) } answers {
            0
        }

        val result = productGatewayImpl.deleteById(id)

        assertFalse(result)
    }
}