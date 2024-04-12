package com.spring.sharding.resouces.gateway

import com.spring.sharding.application.config.ShardingSelector
import com.spring.sharding.domain.entity.Product
import com.spring.sharding.domain.gateway.ProductGateway
import com.spring.sharding.resouces.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductGatewayImpl(
    private val productRepository: ProductRepository,
    private val shardingSelector: ShardingSelector
) : ProductGateway {

    override fun findById(id: String): Product? {
        return shardingSelector.selectSharding(id) {
            productRepository.findById(id)
        }
    }

    override fun save(product: Product): Boolean {
        return shardingSelector.selectSharding(product.id) {
            productRepository.save(product) > 0
        }
    }

    override fun deleteById(id: String): Boolean {
        return shardingSelector.selectSharding(id) {
            productRepository.delete(id) > 0
        }
    }
}