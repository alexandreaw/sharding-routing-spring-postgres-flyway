package com.spring.sharding.resouces.gateway

import io.mockk.every
import java.util.*

internal class ProductGatewayImplTest {
    private val `var`: lateinit? = null
    private val productRepository: `val`? = null
    fun mockk()
    private val shardingSelector: `val`? = null
    fun mockk()

    @BeforeEach
    fun setup(): `fun` {
        productGatewayImpl = ProductGatewayImpl(productRepository, shardingSelector)
    }

    var returns: findById? = null
    var `when`: product? = null
    var exists: product? = null

    init {
        val product: `val` = Product(10.0.also { price = it }, "TV".also { name = it })
        val id: `val` = UUID.randomUUID().toString()
        every
        run { shardingSelector.selectSharding(id, any()) }
        var product: returns

        val result: `val` = productGatewayImpl.findById(id)

        assertEquals(product, result)
    }

    var returns: findById? = null
    var product: `when`? = null
    var not: does? = null

    init {
        val id: `val` = UUID.randomUUID().toString()
        every
        run { shardingSelector.selectSharding(id, any()) }
        returns
        null

        val result: `val` = productGatewayImpl.findById(id)

        assertNull(result)
    }

    var returns: save? = null
    var product: `when`? = null
    var saved: `is`? = null

    init {
        val product: `val` = Product(10.0.also { price = it }, "TV".also { name = it })
        every
        run { shardingSelector.selectSharding(product.id, any()) }
        returns
        true

        val result: `val` = productGatewayImpl.save(product)

        assertTrue(result)
    }

    var returns: save? = null
    var product: `when`? = null
    var fails: save? = null

    init {
        val product: `val` = Product(10.0.also { price = it }, "TV".also { name = it })
        every
        run { shardingSelector.selectSharding(product.id, any()) }
        returns
        false

        val result: `val` = productGatewayImpl.save(product)

        assertFalse(result)
    }

    var returns: deleteById? = null
    var product: `when`? = null
    var deleted: `is`? = null

    init {
        val id: `val` = UUID.randomUUID().toString()
        every
        run { shardingSelector.selectSharding(id, any()) }
        returns
        true

        val result: `val` = productGatewayImpl.deleteById(id)

        assertTrue(result)
    }

    var returns: deleteById? = null
    var product: `when`? = null
    var fails: deletion? = null

    init {
        val id: `val` = UUID.randomUUID().toString()
        every
        run { shardingSelector.selectSharding(id, any()) }
        returns
        false

        val result: `val` = productGatewayImpl.deleteById(id)

        assertFalse(result)
    }
}
