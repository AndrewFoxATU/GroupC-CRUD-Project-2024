
-- Drop database if exists
CREATE DATABASE IF NOT EXISTS SubwayDatabase;

-- Use the newly created database
USE SubwayDatabase;

-- Table to store information about customers
CREATE TABLE Customers (
    CustomerID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(50),
    Email VARCHAR(100),
    Phone VARCHAR(15)
);

-- Table to store information about sandwiches
CREATE TABLE Sandwiches (
    SandwichID INT PRIMARY KEY,
    Name VARCHAR(100),
    Price DECIMAL(10, 2)
);


-- Table to store information about orders
CREATE TABLE Orders (
    OrderID INT AUTO_INCREMENT PRIMARY KEY,
    CustomerID INT,
    OrderDate DATE,
    TotalPrice DECIMAL(10, 2),
    FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID)
);

-- Table to store the many-to-many relationship between orders and sandwiches
CREATE TABLE OrderSandwiches (
    OrderID INT,
    SandwichID INT,
    Quantity INT,
    PRIMARY KEY (OrderID, SandwichID),
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (SandwichID) REFERENCES Sandwiches(SandwichID)
);

-- Insert sample data into Customers table
INSERT INTO Customers (CustomerID, Name, Email, Phone)
VALUES
    (1, 'John Doe', 'john.doe@example.com', '123-456-7890'),
    (2, 'Jane Smith', 'jane.smith@example.com', '987-654-3210');

-- Insert sample data into Sandwiches table
INSERT INTO Sandwiches (SandwichID, Name, Price)
VALUES
    (1, 'Italian BMT', 6.99),
    (2, 'Chicken Fajita', 5.99),
    (3, 'BBQ Rib', 4.99);

-- Insert sample data into Orders table
INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalPrice)
VALUES
    (1, 1, '2024-03-14', 12.98), -- John Doe's order
    (2, 2, '2024-03-14', 9.98);    -- Jane Smith's order

-- Insert sample data into OrderSandwiches table
INSERT INTO OrderSandwiches (OrderID, SandwichID, Quantity)
VALUES
    (1, 1, 2), -- John Doe ordered 2 Italian BMTs
    (2, 2, 1), -- Jane Smith ordered 1 Chicken Fajita
    (2, 3, 1); -- Jane Smith ordered 1 BBQ Rib
