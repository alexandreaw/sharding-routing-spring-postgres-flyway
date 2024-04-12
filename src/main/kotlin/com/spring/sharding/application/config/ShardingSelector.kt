package com.spring.sharding.application.config

import com.spring.sharding.domain.exception.RouteNotFoundException

class ShardingSelector(
    private val shardingProperties: ShardingProperties,
    private val shardingRoutingResolver: ShardingRoutingResolver
) {

    private val routes = shardingRoutingResolver.buildRoutes(shardingProperties)

    fun <T>selectSharding(key: String,  callback: ()-> T): T =
        try {
            val route = routes[shardingRoutingResolver.resolveRouteKey(key)] ?:
                throw RouteNotFoundException()
            CurrentSharding.setDataSourceKey(route)
            callback()
        } finally {
            CurrentSharding.clearDataSourceKey()
        }
}
