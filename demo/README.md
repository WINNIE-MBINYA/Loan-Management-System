# Loan Management System

## Spring Boot Backend API

This project is a **Loan Management System** built with **Spring Boot** and **MySQL**. It provides an **admin-only** interface for managing loans, customers, repayments, and generating loan statistics.

---

## Features

- **User Authentication & Authorization** (JWT-based login & register)
- **Customer Management** (CRUD operations)
- **Loan Issuance** (Admins grant loans with principal, interest, and repayment terms)
- **Repayment Tracking** (Monitor scheduled and completed payments)
- **Statistical Analysis** (View disbursed vs. repaid loans)

---

## Tech Stack

- **Backend:** Spring Boot, Spring Security, JPA/Hibernate
- **Database:** MySQL
- **Authentication:** JWT
- **Build Tool:** Maven/Gradle
- **IDE:** IntelliJ IDEA

---

## Project Structure

```
loan-management-system/
│── src/
│   ├── main/
│   │   ├── java/com/loanmanagement/
│   │   │   ├── config/          # Security & App Configurations
│   │   │   ├── controller/      # API Endpoints
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA Entities
│   │   │   ├── exception/       # Global Error Handling
│   │   │   ├── repository/      # JPA Repositories
│   │   │   ├── service/         # Business Logic
│   │   │   ├── utils/           # Helper Methods
│   │   ├── resources/
│   │   │   ├── application.properties  # Database & JWT Config
│── pom.xml (if using Maven)  
│── build.gradle (if using Gradle)  
│── README.md  
```

---

## Authentication & Authorization

### Register a New Admin
```http
POST /api/auth/register
```
**Request Body:**
```json
{
  "username": "admin123",
  "password": "securePass",
  "role": "ADMIN"
}
```

### Login
```http
POST /api/auth/login
```
**Response:**
```json
{
  "token": "your-jwt-token-here"
}
```
Use this **JWT token** in the `Authorization` header for all admin-only requests.

---

## Loan Management APIs

### Issue a Loan
```http
POST /api/loans/issueLoan
```
**Request Body:**
```json
{
  "customer": { "id": 1 },
  "principalAmount": 5000,
  "interestRate": 5.5,
  "repaymentPeriod": 12,
  "repaymentFrequency": "MONTHLY"
}
```

### Get All Loans
```http
GET /api/loans
```

### View Loan Repayment Schedule
```http
GET /api/loans/{loanId}/schedule
```

---

## Statistical Analysis

### Loans Disbursed vs. Paid
```http
GET /api/statistics/loansSummary
```

### Total Pending Payments
```http
GET /api/statistics/pendingPayments
```

---

## Setup & Installation

### Clone the Repository
```sh
git clone https://github.com/WINNIE-MBINYA/loan-management-system.git
cd loan-management-system
```

### Configure Database (MySQL)
Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/loan_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### Run the Application
```sh
mvn spring-boot:run
```
or
```sh
./gradlew bootRun
```

---

## Next Steps

- Improve error handling
- Implement loan approval workflows
- Add more detailed financial reports

---

## Contributing

Feel free to fork, raise issues, or submit pull requests! 🚀

