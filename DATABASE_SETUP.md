# Database & Server Setup Guide

This guide explains how to set up the MySQL database using XAMPP and run the Spring Boot application.

## 1. MySQL Setup with XAMPP

### Step 1: Start MySQL
1. Open **XAMPP Control Panel**.
2. Click **Start** next to **MySQL**.
3. Ensure the module turns green and runs on port **3306**.

### Step 2: Create Database & Import Data
1. Open your browser and go to [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
2. Click **New** in the left sidebar.
3. Enter database name: `pizza_db`.
4. Select collation: `utf8mb4_general_ci`.
5. Click **Create**.
6. Click on the `pizza_db` database in the left sidebar.
7. Click the **Import** tab at the top.
8. Click **Choose File** and select the `database.sql` file from your project folder:
   - Path: `c:\Users\HP\Desktop\pizza__app\database.sql`
9. Click **Import** at the bottom of the page.
10. You should see a success message and tables (`users`, `products`, `orders`, etc.) in the left sidebar.

## 2. Server Details

### Backend Server
- **Server Type**: Embedded Tomcat Server (Spring Boot default).
- **Why?**: It is lightweight, production-ready, and requires no external installation.
- **Port**: `8080` (Default).
- **Communication**: The frontend communicates with the backend via HTTP REST API calls (e.g., `GET /api/products`, `POST /api/orders`).

### Connection Verification
- When you run the application, Spring Boot will automatically connect to MySQL.
- If the connection is successful, you will see logs like `HikariPool-1 - Added connection com.mysql.cj.jdbc.ConnectionImpl...`.
- If it fails, ensure XAMPP MySQL is running and the username/password in `application.properties` matches your XAMPP setup (default: `root` / empty).

## 3. Running the Application

1. Open a terminal in the project root.
2. Run: `mvn spring-boot:run`
3. The app will start on `http://localhost:8080`.

## 4. Verifying Data Persistence
1. Open the app in your browser.
2. Place an order or register a user.
3. Go to **phpMyAdmin** and browse the `orders` or `users` table.
4. You should see the new records appear immediately.
