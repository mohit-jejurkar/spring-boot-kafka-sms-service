# 📡 SMS Kafka Platform (Spring Boot Microservice)

A Spring Boot-based SMS platform that authenticates users, sends SMS messages via Kafka, and integrates with telco APIs. This project demonstrates clean architecture, SOLID principles, logging, and exception handling throughout a microservice pipeline.

---

## 🚀 Features

- ✅ REST API for sending SMS with user authentication
- ✅ Kafka producer/consumer for decoupled message transmission
- ✅ Telco endpoint simulation with dynamic response
- ✅ Scheduled processor for pushing messages to external telco
- ✅ PostgreSQL persistence for message and user metadata
- ✅ Centralized constants and clean logging
- ✅ Built with Spring Boot, Kafka, JPA, and REST

---

## 🧱 Project Structure

src/main/java/com/example/smsplatform/
├── controller/ → REST controllers (API entry points)
├── domain/
│ ├── entity/ → JPA entities (User, SmsMessage)
│ └── repository/ → Spring Data repositories
├── service/
│ ├── publisher/ → Kafka producer
│ ├── consumer/ → Kafka consumer
│ └── processor/ → Scheduled message dispatcher
├── util/ → Constants and utilities
├── Application.java → Main Spring Boot entry point

---

## 📬 API Endpoints

### 1. **Send SMS**
GET /telco/sendmsg

**Params:**
- `username`
- `password`
- `mobile`
- `message`

**Success Response:**
STATUS: ACCEPTEDRESPONSE_CODE: SUCCESS<ack_id>



**Failure Response:**
STATUS: REJECTED~~RESPONSE_CODE: FAILURE



### 2. **Telco Receiver (Simulated)**
GET /telco/smsc?account_id=1&mobile=9876543210&message=Hello



---

## 🛠️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Apache Kafka
- PostgreSQL
- REST API
- Jackson (JSON processing)
- Lombok
- Logback + SLF4J (logging)

---

## ⚙️ Configuration

Update the following in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/sms_demo
    username: postgres
    password: postgres

kafka:
  bootstrap-servers: localhost:9092

sms:
  telco:
    url: "http://localhost:8081/telco/smsc?account_id=%d&mobile=%d&message=%s"


🧪 Run Locally
Start Kafka & Zookeeper

1.bin/zookeeper-server-start.sh config/zookeeper.properties
2.bin/kafka-server-start.sh config/server.properties

Start PostgreSQL and create the sms_demo database

Test APIs using Postman or curl

📊 Database Tables

users: Stores credentials and account IDs.
send_msg: Stores SMS messages, statuses, timestamps, telco responses.

👤 Author
Mohit Jejurkar
Backend Developer | Spring Boot | Kafka | Microservices
📍 Pune, India
