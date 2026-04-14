# Hotel Reservation System (Java + JDBC)

##  Overview
This is a console-based Hotel Reservation System built using Java and JDBC.  
It allows users to manage hotel room bookings with basic CRUD operations.

------------------------------------

## Features
- Add Reservation
- View Reservations
- Update Reservation
- Delete Reservation
- Search Room by Reservation ID

------------------------------------

##  Tech Stack
- Java
- JDBC
- MySQL

------------------------------------

##  Setup Instructions

1. Install MySQL
2. Create database:
   ```sql 
   CREATE DATABASE hotel_reservation;

3. Create Reservation table
   * Reservation ID  (primary key)
   * Guest Name
   * Room Number
   * Contact
   * Reservation TimeStamp 
   
 
    CREATE TABLE reservations (
    reservation_id INT PRIMARY KEY,
    guest_name VARCHAR(100),
    room_number INT,
    guest_contact VARCHAR(15),
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

4. Add MySQL Connector JAR to project

5. Run the program
