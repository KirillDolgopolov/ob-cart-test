# ob-cart-test
Test for cart basic microservice (with test coverage)

# 🛒 Cart Service

A lightweight Java backend application for managing shopping carts in an e-commerce environment.  
Built with **Spring Boot**, the service provides cart and product management features, operating entirely in-memory.

---

## ✅ Features

- Create a new shopping cart with a unique system-generated ID
- Add one or more products to a cart
- Retrieve cart details by ID
- Automatically delete carts after 10 minutes of inactivity
- Manually delete a cart
- In-memory storage (volatile, no persistence)
- RESTful API with clean structure
- Unit and integration tests with high coverage

---

## 🧱 Tech Stack

- Java 17  
- Spring Boot  
- JUnit + Mockito  
- Postman collection for testing endpoints  
- In-memory storage (ConcurrentHashMap based)  


