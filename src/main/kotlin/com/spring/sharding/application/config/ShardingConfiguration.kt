package com.spring.sharding.application.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ShardingProperties::class)
class ShardingConfiguration() {

    @Bean
    fun routingDataSource(
        shardingProperties: ShardingProperties
    ) = ShardingRoutingDataSource(shardingProperties)

    @Bean
    fun datasourceRouter(
        shardingProperties: ShardingProperties,
        shardingRoutingResolver: ShardingRoutingResolver
    ) = ShardingSelector(shardingProperties, shardingRoutingResolver)
}