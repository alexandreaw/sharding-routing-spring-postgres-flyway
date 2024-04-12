package com.spring.sharding.domain.entity

import java.util.*

data class Product(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var price: Double
)