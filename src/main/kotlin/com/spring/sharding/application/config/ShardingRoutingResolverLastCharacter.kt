package com.spring.sharding.application.config

import org.springframework.stereotype.Component

@Component
class ShardingRoutingResolverLastCharacter: ShardingRoutingResolver {
    override fun resolveRouteKey(key: String): String = key.last().toString()

    override fun buildRoutes(shardingProperties: ShardingProperties): Map<String, String> {
        return shardingProperties.rotesResolvedConfig.flatMap {
            buildAllPossibleRoutesBasedOnConfig(it)
        }.toMap()
    }

    private fun buildAllPossibleRoutesBasedOnConfig(routeConfig: ShardingRouteConfig): List<Pair<String, String>> {
        val letters = routeConfig.route[0]..routeConfig.route[2]
        val number = routeConfig.route[3]..routeConfig.route[5]

        val allCombinations = letters.toSet() + number.toSet()

        return allCombinations.map {
            it.toString().lowercase() to routeConfig.name
        }
    }

}
