# UPI Payment Simulator

A backend-driven UPI Payment Simulator that mimics real UPI flows such as
registration, login, UPI ID creation, PIN validation, balance check, and
money transfer between users.

This project is built to demonstrate real-world FinTech backend concepts
using Spring Boot and JWT authentication.

---

## 🚀 Features

- User Registration & Login
- JWT-based Authentication
- Create UPI ID
- Set & Validate UPI PIN
- Add & Check Bank Balance
- Send Money between UPI users
- Transaction History
- Global Exception Handling
- Simple HTML + CSS UI

---

## 🧠 UPI Flow Implemented

Register → Login → JWT Token
↓
Create UPI ID → Set UPI PIN
↓
Add Balance → Send Money → View Transactions


---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security (JWT)
- Hibernate / JPA
- Oracle Database
- Maven
- HTML, CSS, JavaScript
- Git & GitHub

---

## ⚙️ How to Run the Project

1. Clone the repository
   ```bash
   git clone https://github.com/ga186/upi-payment-simulator.git
Configure Oracle DB in application.properties

Run the Spring Boot application

mvn spring-boot:run


Open browser

http://localhost:8080

🔐 Security

Stateless JWT Authentication

Passwords & UPI PINs are stored in encrypted form

All payment APIs are secured

👨‍💻 Author

Anil Kumar
Java Backend Developer (Fresher)

📌 Purpose

This project was built to understand and demonstrate how real UPI-based
FinTech systems work internally and to prepare for backend Java interviews.