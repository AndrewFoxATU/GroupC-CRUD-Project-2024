-- subwaydatabase.sql
 
-- Drop database if exists
CREATE DATABASE IF NOT EXISTS SubwayDatabase;
 
-- Use the newly created database
USE SubwayDatabase;
 
-- Table to store information about customers
CREATE TABLE Customers (
    CustomerID INT PRIMARY KEY,
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
 
-- Table to store information about ingredients
CREATE TABLE Ingredients (
    IngredientID INT PRIMARY KEY,
    Name VARCHAR(100),
    Price DECIMAL(10, 2)
);
 
-- Table to store the many-to-many relationship between sandwiches and ingredients
CREATE TABLE SandwichIngredients (
    SandwichID INT,
    IngredientID INT,
    PRIMARY KEY (SandwichID, IngredientID),
    FOREIGN KEY (SandwichID) REFERENCES Sandwiches(SandwichID),
    FOREIGN KEY (IngredientID) REFERENCES Ingredients(IngredientID)
);
 
-- Table to store information about orders
CREATE TABLE Orders (
    OrderID INT PRIMARY KEY,
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
 
-- Insert sample data into Ingredients table
INSERT INTO Ingredients (IngredientID, Name, Price)
VALUES
    (1, 'Ham', 1.50),
    (2, 'Pepperoni', 1.75),
    (3, 'Salami', 1.80),
    (4, 'Turkey', 2.00),
    (5, 'Lettuce', 0.50),
    (6, 'Tomato', 0.75),
    (7, 'Cucumber', 0.60);
 
-- Insert sample data into SandwichIngredients table
INSERT INTO SandwichIngredients (SandwichID, IngredientID)
VALUES
    (1, 1), -- Italian BMT contains Ham
    (1, 2), -- Italian BMT contains Pepperoni
    (1, 3), -- Italian BMT contains Salami
    (2, 4), -- Chicken Fajita contains Turkey
    (2, 5), -- Chicken Fajita contains Lettuce
    (2, 6), -- Chicken Fajita contains Tomato
    (3, 5), -- BBQ Rib contains Lettuce
    (3, 6), -- BBQ Rib contains Tomato
    (3, 7); -- BBQ Rib contains Cucumber
 
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