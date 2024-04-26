package com.spring.sharding.application.config

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer

class PgContainer : PostgreSQLContainer<PgContainer> {
  constructor(image: String, port: Int): super(image) {
    addFixedExposedPort(port, 5432)
  }
}