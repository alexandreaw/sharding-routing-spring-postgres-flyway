package com.spring.sharding.resouces.repository

import com.spring.sharding.domain.entity.Product
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun findById(id: String): Product? = try {
        val query = "SELECT * FROM products WHERE id = ?"
        jdbcTemplate.queryForObject(query, arrayOf(id)) { rs, _ ->
            Product(
                id = rs.getString("id"),
                name = rs.getString("name"),
                price = rs.getDouble("price")
            )
        }
    } catch (ex: EmptyResultDataAccessException) {
        null
    }

    fun save(product: Product): Int {
        val query = "INSERT INTO products (id, name, price) VALUES (?, ?, ?)"
        return jdbcTemplate.update(query, product.id, product.name, product.price)
    }

    fun delete(id: String): Int {
        val query = "DELETE FROM products WHERE id = ?"
        return jdbcTemplate.update(query, id)
    }
}
