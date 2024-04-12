package com.spring.sharding.application.config

interface ShardingRoutingResolver {

    fun resolveRouteKey(key: String): String
    fun buildRoutes(shardingProperties: ShardingProperties): Map<String, String>
}
