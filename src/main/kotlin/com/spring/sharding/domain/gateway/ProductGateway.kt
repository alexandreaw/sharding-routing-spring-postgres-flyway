package com.spring.sharding.domain.gateway

import com.spring.sharding.domain.entity.Product

interface ProductGateway {
    fun findById(id: String): Product?
    fun save(product: Product): Boolean
    fun deleteById(id: String): Boolean
}
