# sharding-routing-spring-postgres-flyway

## What is Sharding
Sharding is a database partitioning technique where data is horizontally divided into smaller subsets, known as shards, and distributed across multiple databases or nodes. It's used to improve scalability, performance, and availability in large-scale distributed systems by spreading the load across multiple resources.

## Why to Use Sharding
There are several reasons to use sharding in a database system:
- **Scalability**: Sharding allows the system to scale horizontally by distributing data across multiple nodes, enabling it to handle increasing amounts of data and traffic.
- **Performance**: By distributing data and workload, sharding can improve query performance and reduce response times.
- **Availability**: Sharding can improve fault tolerance and availability by reducing the impact of failures on the entire system. If one shard or node fails, the rest of the system can continue to operate.
- **Isolation**: Sharding can provide isolation between different sets of data, allowing for better resource utilization and performance optimization.

## Stack
The following stack is used in this project:

* **PostgreSQL**: A powerful, open-source relational database management system known for its reliability, robustness, and extensibility.
* **Spring Framework**: An open-source framework for building Java applications, known for its dependency injection, declarative programming, and comprehensive support for enterprise features.
* **Flyway**: A database migration tool that simplifies versioning and managing changes to database schemas, making it easier to keep database structures in sync with application code.

## Understanding Better with a Example
Scenario 1: In this scenario, when you register a product using a RESTful service, you receive an ID that ends with "e". This means that according to the routing setup "pg-a: A-N0-4", which includes all IDs ending from "A" to "N" and from "0" to "4", the product will be directed to the PG-A database.

* ![Sharding_1](https://github.com/alexandreaw/sharding-routing-spring-postgres-flyway/assets/793210/995b243d-eedc-4a75-85fd-6b47e51497e0)
