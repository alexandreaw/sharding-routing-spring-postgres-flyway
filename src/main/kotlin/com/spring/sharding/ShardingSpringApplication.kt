package com.spring.sharding

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShardingSpringApplication

fun main(args: Array<String>) {
	runApplication<ShardingSpringApplication>(*args)
}
