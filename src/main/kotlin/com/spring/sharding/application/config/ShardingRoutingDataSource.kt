package com.spring.sharding.application.config

import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class ShardingRoutingDataSource(
    private val shardingProperties: ShardingProperties
) : AbstractRoutingDataSource() {

    init {
        setTargetDataSources(
            shardingProperties.resolvedDataSourcesConfig.associate { config ->
                config.name to HikariDataSource(config.dataSourceConfig)
            }
        )
    }

    override fun determineCurrentLookupKey(): Any? =
        CurrentSharding.dataSourceKey.get()

    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
        flywayMigrate()
    }

    private fun flywayMigrate() {
        resolvedDataSources.forEach { dataSources ->
            Flyway.configure()
                .locations("classpath:/db/migration")
                .dataSource(dataSources.value)
                .load()
                .migrate()
        }
    }

}

object CurrentSharding {
    val dataSourceKey: ThreadLocal<String> = ThreadLocal()

    fun setDataSourceKey(dataSource: String) {
        dataSourceKey.set(dataSource)
    }

    fun clearDataSourceKey() {
        dataSourceKey.remove()
    }
}