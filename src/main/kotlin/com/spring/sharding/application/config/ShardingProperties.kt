package com.spring.sharding.application.config

import com.zaxxer.hikari.HikariConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

class ShardingRouteConfig (val name: String, val route: String)
class ShardingDataSourceConfig(val name: String, val dataSourceConfig: HikariConfig)

@ConfigurationProperties(prefix = "spring.sharding")
@ConstructorBinding
class ShardingProperties (
    private val datasource: Map<String, HikariConfig> = HashMap(),
    private val route: Map<String, String>
) {
    val rotesResolvedConfig = route.map {
        ShardingRouteConfig(it.key, it.value)
    }

    val resolvedDataSourcesConfig = datasource.map {
        it.value.poolName = it.key
        ShardingDataSourceConfig(it.key, it.value)
    }
}
