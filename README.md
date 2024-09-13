# E-CommerceApp
___
E-CommerceApp is a comprehensive e-commerce platform built using [Spring Boot](https://spring.io/projects/spring-boot) and various microservices. This project aims to provide a scalable and maintainable architecture for an online shopping application.

## Table of Contents
___

- [Features](#features)
- [Architecture](#architecture)
- [Microservices](#microservices)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [Testing](#testing)

## Features
___

- Product catalog management
- Order processing
- Payment integration
- Notification service
- API Gateway for routing
- Service discovery with [Eureka](https://www.google.com/search?q=eureka+service+discovery)
- Centralized configuration with [Spring Cloud Config](https://spring.io/projects/spring-cloud-config)

## Architecture
___

The application follows a microservices architecture, with each service responsible for a specific domain. The services communicate with each other using REST APIs.

## Microservices
___

### Config Server
___
- **Path:** `services/config-server`
- **Port:** 8888
- **Description:** Centralized configuration server for all microservices.

### Discovery Service
___
- **Path:** `services/discovery`
- **Port:** 8761
- **Description:** Eureka server for service discovery.

### Gateway Service
___
- **Path:** `services/gateway`
- **Port:** Configured via `application.yml`
- **Description:** API Gateway for routing requests to appropriate microservices.

### Product Service
___
- **Path:** `services/product`
- **Port:** 8083
- **Description:** Manages product catalog.

### Order Service
___
- **Path:** `services/order`
- **Port:** Configured via `application.yml`
- **Description:** Handles order processing.

### Payment Service
___
- **Path:** `services/payment`
- **Port:** Configured via `application.yml`
- **Description:** Manages payment transactions.

### Notification Service
___
- **Path:** `services/notification`
- **Port:** Configured via `application.yml`
- **Description:** Sends email notifications for various events.

### Customer Service
___
- **Path:** `services/customer`
- **Port:** Configured via `application.yml`
- **Description:** Manages customer information.

## Configuration
___

Configuration for each service is managed centrally using the Config Server. The configuration files are located in the `configurations` directory within the Config Server.

Example configuration file for `order-service`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/orders
    username: root
    password: akshay
```

## Running the Application
___

1. Clone the repository
```shell
  git clone https://github.com/yourusername/E-CommerceApp.git
  cd E-CommerceApp
```
2. Start the config server
```shell
    cd services/config-server
  ./mvnw spring-boot:run
```
3. Start the discovery server
```shell
    cd ../discovery
    ./mvnw spring-boot:run
```
4. Start the gateway service
```shell
  cd ../gateway
  ./mvnw spring-boot:run
```
5. Start the remaining microservice
```shell
cd ../product
./mvnw spring-boot:run

cd ../order
./mvnw spring-boot:run

cd ../payment
./mvnw spring-boot:run

cd ../notification
./mvnw spring-boot:run

cd ../customer
./mvnw spring-boot:run
```

## Testing
___
Unit and integration tests are located in the src/test/java directory of each microservice. To run the tests, navigate to the microservice directory and execute:
```shell
 ./mvnw test
```

## Contribution
___
Contributions are welcome! Please fork the repository and create a pull request with your changes. Ensure that your code adheres to the project's coding standards and includes appropriate tests.

## Contact
___
- [LinkedIn - Akshay Baviskar](www.linkedin.com/in/akshay-baviskar-connect)

