-- Create database schema for Digital Banking System
-- Oracle Database

-- Create sequences
CREATE SEQUENCE customers_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE loans_seq START WITH 1 INCREMENT BY 1;

-- Create customers table
CREATE TABLE customers (
    id NUMBER(19) PRIMARY KEY,
    cpf VARCHAR2(11) UNIQUE NOT NULL,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(255) UNIQUE NOT NULL,
    password VARCHAR2(255) NOT NULL,
    phone VARCHAR2(20),
    address VARCHAR2(500),
    balance NUMBER(10,2) DEFAULT 0.00 NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL,
    role VARCHAR2(20) DEFAULT 'CUSTOMER' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create loans table
CREATE TABLE loans (
    id NUMBER(19) PRIMARY KEY,
    customer_id NUMBER(19) NOT NULL,
    amount NUMBER(10,2) NOT NULL,
    installments NUMBER(3) NOT NULL,
    interest_rate NUMBER(5,2) NOT NULL,
    total_amount NUMBER(10,2) NOT NULL,
    monthly_payment NUMBER(10,2) NOT NULL,
    status VARCHAR2(20) DEFAULT 'PENDING' NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_loan_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Create indexes
CREATE INDEX idx_customers_cpf ON customers(cpf);
CREATE INDEX idx_customers_email ON customers(email);
CREATE INDEX idx_customers_status ON customers(status);
CREATE INDEX idx_loans_customer_id ON loans(customer_id);
CREATE INDEX idx_loans_status ON loans(status);

-- Insert default manager user
INSERT INTO customers (id, cpf, name, email, password, role, status, balance)
VALUES (customers_seq.NEXTVAL, '00000000000', 'System Manager', 'manager@banking.com', 
        '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'MANAGER', 'APPROVED', 0.00);

COMMIT;