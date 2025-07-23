# Digital Banking System

A complete digital banking system built with Java Spring Boot backend and Angular 16 frontend.

## Features

### Backend (Spring Boot)
- **Customer Management**: Registration, approval workflow, profile management
- **Authentication & Authorization**: JWT-based auth with role-based access control
- **Loan Management**: Loan requests with business rules validation
- **Async Messaging**: RabbitMQ integration for notifications
- **Database**: Oracle Database with JPA/Hibernate
- **API Documentation**: Swagger/OpenAPI 3
- **Testing**: Comprehensive unit and integration tests

### Frontend (Angular 16)
- **Responsive Design**: Modern Material Design UI
- **Role-based Navigation**: Different interfaces for customers and managers
- **Real-time Updates**: WebSocket integration for notifications
- **Form Validation**: Reactive forms with validation
- **Security**: JWT token management and route guards

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Spring Security (JWT Authentication)
- Spring Data JPA
- Hibernate ORM
- Oracle Database
- RabbitMQ
- Lombok
- Maven
- JUnit 5
- Swagger/OpenAPI

### Frontend
- Angular 16
- Angular Material
- Bootstrap 5
- TypeScript
- RxJS
- SCSS

## Business Rules

1. **Customer Registration**: 
   - Customers register and wait for manager approval
   - Only approved customers can access banking features

2. **Loan System**:
   - Maximum loan amount: R$ 10,000.00
   - Maximum installments: 24 months
   - Interest rate: 1% per month
   - One active loan per customer

3. **Roles**:
   - **CUSTOMER**: Access to personal dashboard, balance, loan requests
   - **MANAGER**: Approve customer registrations, manage system

## Installation & Setup

### Prerequisites
- Java 17
- Node.js 18+
- Oracle Database 19c+
- RabbitMQ
- Maven

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd digital-banking-system/backend
   ```

2. **Database Setup**
   - Create Oracle database schema
   - Run the SQL script: `src/main/resources/scripts/oracle-schema.sql`

3. **Environment Variables**
   ```bash
   export DB_USERNAME=your_db_username
   export DB_PASSWORD=your_db_password
   export RABBITMQ_HOST=localhost
   export RABBITMQ_USERNAME=guest
   export RABBITMQ_PASSWORD=guest
   export JWT_SECRET=your_jwt_secret_key
   ```

4. **Run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access API Documentation**
   - Swagger UI: http://localhost:8080/swagger-ui.html

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd digital-banking-system/frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Run the application**
   ```bash
   ng serve
   ```

4. **Access the application**
   - Frontend: http://localhost:4200

## Default Credentials

### Manager Account
- Email: manager@banking.com
- Password: password123

## API Endpoints

### Authentication
- `POST /api/auth/register` - Customer registration
- `POST /api/auth/login` - User login

### Customer Endpoints
- `GET /api/customer/profile` - Get customer profile
- `GET /api/customer/loans` - Get customer loans
- `POST /api/customer/loan/request` - Request a loan

### Admin Endpoints (Manager only)
- `GET /api/admin/customers/pending` - Get pending customers
- `POST /api/admin/customers/{id}/approve` - Approve customer

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
ng test
```

## Project Structure

```
digital-banking-system/
├── backend/
│   ├── src/main/java/com/banking/
│   │   ├── config/           # Configuration classes
│   │   ├── controller/       # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Custom exceptions
│   │   ├── repository/      # JPA repositories
│   │   ├── security/        # Security components
│   │   ├── service/         # Business logic
│   │   └── messaging/       # RabbitMQ components
│   ├── src/main/resources/
│   │   ├── scripts/         # SQL scripts
│   │   └── application.yml  # Configuration
│   └── src/test/           # Test cases
├── frontend/
│   ├── src/app/
│   │   ├── components/     # Angular components
│   │   ├── services/       # Angular services
│   │   ├── guards/         # Route guards
│   │   ├── interceptors/   # HTTP interceptors
│   │   └── models/         # TypeScript interfaces
│   └── environments/       # Environment configs
└── README.md
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please open an issue in the repository.
