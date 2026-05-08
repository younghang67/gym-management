# Gym API

A comprehensive Spring Boot REST API for managing gym operations, including user authentication, membership management, and fitness tracking.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Security](#security)
- [Contributing](#contributing)

## 🎯 Overview

The Gym API is a backend service built with Spring Boot that provides complete functionality for managing a gym facility. It handles user authentication, membership plans, fitness goal tracking, and member records management. The API is secured with JWT tokens and implements role-based access control.

## ✨ Features

- **User Management**
  - User registration with validation
  - User authentication and login
  - Role-based access control (Admin, User)
  - User profile management

- **Authentication & Security**
  - JWT-based token authentication
  - Password encryption using Spring Security
  - CORS configuration for frontend integration
  - Method-level security with @PreAuthorize

- **Membership Management**
  - Create and manage membership plans
  - Track membership pricing and duration
  - Assign memberships to users
  - Monitor membership status

- **Fitness Tracking**
  - Set and manage fitness goals (target weight, BMI)
  - Record fitness measurements and progress
  - Track user fitness records over time

- **Admin Dashboard**
  - Admin-only endpoints for system management
  - Dashboard access control

## 🛠 Tech Stack

- **Java 21** - Programming language
- **Spring Boot 4.0.6** - Framework
- **Spring Data JPA** - ORM and database access
- **Spring Security** - Authentication and authorization
- **MySQL** - Database
- **JWT (jjwt)** - Token-based authentication
- **Lombok** - Reduce boilerplate code
- **Maven** - Build and dependency management

## 📦 Prerequisites

Before running this project, ensure you have:

- **Java 21+** installed
- **MySQL 8.0+** installed and running
- **Maven 3.8+** installed
- **Git** installed (for cloning)

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd api
```

### 2. Create Database
```sql
CREATE DATABASE gym_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Update Configuration (Optional)
Edit `src/main/resources/application.yml` if needed (see Configuration section)

### 4. Build the Project
```bash
./mvnw clean install
```

On Windows:
```bash
mvnw.cmd clean install
```

## ⚙️ Configuration

Edit `src/main/resources/application.yml` to customize:

```yaml
spring:
  application:
    name: api

  datasource:
    url: jdbc:mysql://localhost:3306/gym_db?serverTimezone=UTC
    username: root          # Update with your MySQL username
    password:               # Update with your MySQL password
    driver-class-name: com.mysql.cj.jdbc.Driver

  jpa:
    hibernate:
      ddl-auto: update     # auto, create, create-drop, validate, update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

jwt:
  secret: dGhpc2lzYXZlcnlsb25nYW5kc2VjdXJlYmFzZTY0a2V5Zm9yand0  # Change in production
  expiration: 86400000    # Token expiration time in milliseconds (24 hours)
```

### Important Configuration Notes:
- **Change JWT Secret**: Generate a secure Base64-encoded string for production
- **Database Credentials**: Update MySQL username and password
- **CORS Origin**: Currently set to `http://localhost:3000` in UserController
- **Hibernate DDL**: Set to `update` for development, consider `validate` for production

## 🏃 Running the Application

### Option 1: Using Maven
```bash
./mvnw spring-boot:run
```

### Option 2: Using JAR file
```bash
./mvnw clean package
java -jar target/api-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### Authentication Endpoints

#### Register User
```
POST /api/users/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}

Response: 201 Created
{
  "id": 1,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "USER",
  "isActive": true
}
```

#### Login
```
POST /api/users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response: 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### Admin Dashboard (Protected)
```
GET /api/users/admin/dashboard
Authorization: Bearer {token}

Response: 200 OK
"Admin Access"
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/gym/api/
│   │   ├── ApiApplication.java          # Main Spring Boot application
│   │   ├── config/
│   │   │   ├── CorsConfig.java          # CORS configuration
│   │   │   └── SecurityConfig.java      # Spring Security configuration
│   │   ├── controller/
│   │   │   └── UserController.java      # REST API endpoints
│   │   ├── dto/
│   │   │   ├── AuthResponse.java        # Login response DTO
│   │   │   ├── LoginRequest.java        # Login request DTO
│   │   │   ├── RegisterRequest.java     # Register request DTO
│   │   │   ├── MembershipRequest.java   # Membership request DTO
│   │   │   └── ErrorResponse.java       # Error response DTO
│   │   ├── entity/
│   │   │   ├── User.java                # User entity
│   │   │   ├── Role.java                # Role enum (ADMIN, USER)
│   │   │   ├── Status.java              # Status enum
│   │   │   ├── Membership.java          # Membership plans
│   │   │   ├── UserMembership.java      # User-Membership mapping
│   │   │   ├── FitnessGoal.java         # User fitness goals
│   │   │   └── FitnessRecord.java       # Fitness tracking records
│   │   ├── repository/
│   │   │   └── UserRepository.java      # User data access layer
│   │   ├── security/
│   │   │   ├── JwtService.java          # JWT token generation/validation
│   │   │   └── JwtAuthFilter.java       # JWT authentication filter
│   │   ├── service/
│   │   │   └── UserService.java         # User business logic
│   │   └── seeder/
│   │       └── DataBaseSeeder.java      # Initialize database with test data
│   └── resources/
│       └── application.yml              # Application configuration
└── test/
    └── java/com/gym/api/
        └── ApiApplicationTests.java    # Unit tests
```

## 🗄️ Database Schema

### Users Table
- `id` - Primary key (auto-increment)
- `name` - User full name
- `email` - Unique email address
- `password` - Encrypted password
- `role` - User role (ADMIN, USER)
- `phone_number` - Unique phone number
- `is_active` - Account status
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp

### Memberships Table
- `id` - Primary key (auto-increment)
- `name` - Membership plan name
- `price` - Membership price (BigDecimal)
- `duration_days` - Membership validity period in days
- `description` - Plan description
- `created_at` - Creation timestamp
- `updated_at` - Last update timestamp

### User Memberships Table
- `id` - Primary key (auto-increment)
- `user_id` - Foreign key to users
- `membership_id` - Foreign key to memberships
- `start_date` - Membership start date
- `end_date` - Membership end date
- `status` - Membership status (ACTIVE, EXPIRED, CANCELLED)

### Fitness Goals Table
- `id` - Primary key (auto-increment)
- `user_id` - Foreign key to users
- `target_weight` - Target weight in kg
- `target_bmi` - Target BMI

### Fitness Records Table
- `id` - Primary key (auto-increment)
- `user_id` - Foreign key to users
- `weight` - Current weight in kg
- `bmi` - Current BMI
- `recorded_at` - Record timestamp

## 🔒 Security

### Features
- **JWT Authentication**: Stateless token-based authentication
- **Password Encoding**: Bcrypt password hashing via Spring Security
- **CORS**: Configured to accept requests from `http://localhost:3000`
- **Role-Based Access**: Support for ADMIN and USER roles
- **Method Security**: @PreAuthorize annotations for endpoint protection

### Best Practices
1. Store sensitive configuration in environment variables
2. Change the JWT secret in production
3. Use HTTPS in production environment
4. Regularly update dependencies for security patches
5. Implement rate limiting for API endpoints
6. Add logging and monitoring

## 🤝 Contributing

1. Create a feature branch (`git checkout -b feature/amazing-feature`)
2. Commit your changes (`git commit -m 'Add amazing feature'`)
3. Push to the branch (`git push origin feature/amazing-feature`)
4. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or issues, please open an issue in the repository or contact the development team.

## 📚 Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JWT Documentation](https://jwt.io/)
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Last Updated**: May 2026
**Version**: 0.0.1-SNAPSHOT
