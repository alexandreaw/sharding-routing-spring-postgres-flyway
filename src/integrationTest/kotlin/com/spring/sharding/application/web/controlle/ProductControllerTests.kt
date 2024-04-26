package com.spring.sharding.application.web.controlle

import com.fasterxml.jackson.databind.ObjectMapper
import com.spring.sharding.application.config.PgContainer
import com.spring.sharding.application.config.ShardingSelector
import com.spring.sharding.application.web.dto.ProductDto
import com.spring.sharding.resouces.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@SpringBootTest
@Testcontainers
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
class ProductControllerTests(

) {
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper
    @Autowired
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var shardingSelector: ShardingSelector

    @Test
    fun `should post product, persist on pg-a and return CREATED`(){
        val productDto = ProductDto()
            .id("8327a4c4-46a7-4329-a292-70eaf473f892")
            .name("Sample Product")
            .price(12.0)

        this.mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().isCreated())

        // when the id ends with 2 should persist on pg-a
        shardingSelector.overrideSharding("pg-a") {
            Assertions.assertNotNull(productRepository.findById(productDto.id))
        }
        shardingSelector.overrideSharding("pg-b") {
            Assertions.assertNull(productRepository.findById(productDto.id))
        }
    }

    @Test
    fun `should post product, persist on pg-b and return CREATED`(){
        val productDto = ProductDto()
            .id("0a74e016-f1f8-472f-b9a8-d1d8bd1efd26")
            .name("Sample Product")
            .price(12.0)

        this.mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().isCreated())

        // when the id ends with 2 should persist on pg-b
        shardingSelector.overrideSharding("pg-b") {
            Assertions.assertNotNull(productRepository.findById(productDto.id))
        }
        shardingSelector.overrideSharding("pg-a") {
            Assertions.assertNull(productRepository.findById(productDto.id))
        }
    }

    init {
        postgreA.start()
        postgreB.start()
    }

    companion object {
        @Container
        val postgreA: PostgreSQLContainer<*> = PgContainer("postgres:13-alpine", 6432)
            .withDatabaseName("sharding")
            .withUsername("sharding")
            .withPassword("sharding")

        @Container
        val postgreB: PostgreSQLContainer<*> = PgContainer("postgres:13-alpine", 6433)
            .withDatabaseName("sharding")
            .withUsername("sharding")
            .withPassword("sharding")
    }
}