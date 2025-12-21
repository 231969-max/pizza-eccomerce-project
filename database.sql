-- Create Database
CREATE DATABASE IF NOT EXISTS pizza_db;
USE pizza_db;

-- Disable foreign key checks for bulk operations
SET FOREIGN_KEY_CHECKS = 0;

-- --------------------------------------------------------
-- Drop Tables if they exist (Clean Setup)
-- --------------------------------------------------------
DROP TABLE IF EXISTS `cart_items`;
DROP TABLE IF EXISTS `carts`;
DROP TABLE IF EXISTS `order_items`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `contact_messages`;

-- --------------------------------------------------------
-- Table Structure for `users`
-- --------------------------------------------------------
CREATE TABLE `users` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `name` VARCHAR(255),
    `role` VARCHAR(50) DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `products`
-- --------------------------------------------------------
CREATE TABLE `products` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(1000),
    `price` DECIMAL(10, 2) NOT NULL,
    `image_url` VARCHAR(255),
    `category` VARCHAR(50),
    `rating` DOUBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `carts`
-- --------------------------------------------------------
CREATE TABLE `carts` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT UNIQUE,
    `total_price` DECIMAL(10, 2) DEFAULT 0.00,
    CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `cart_items`
-- --------------------------------------------------------
CREATE TABLE `cart_items` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `cart_id` BIGINT,
    `product_id` BIGINT,
    `quantity` INT,
    `price` DECIMAL(10, 2),
    CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cart_id`) REFERENCES `carts`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`product_id`) REFERENCES `products`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `orders`
-- --------------------------------------------------------
CREATE TABLE `orders` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT,
    `total_price` DECIMAL(10, 2),
    `status` VARCHAR(50),
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `order_items`
-- --------------------------------------------------------
CREATE TABLE `order_items` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `order_id` BIGINT,
    `product_id` BIGINT,
    `quantity` INT,
    `price` DECIMAL(10, 2),
    CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_order_item_product` FOREIGN KEY (`product_id`) REFERENCES `products`(`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table Structure for `contact_messages`
-- --------------------------------------------------------
CREATE TABLE `contact_messages` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255),
    `email` VARCHAR(255),
    `number` VARCHAR(50),
    `subject` VARCHAR(255),
    `message` TEXT,
    `submitted_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;

-- --------------------------------------------------------
-- Sample Data for `users`
-- --------------------------------------------------------
-- Passwords are 'password' hashed with BCrypt
INSERT INTO `users` (`email`, `password`, `name`, `role`) VALUES
('admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'Admin User', 'ADMIN'),
('user@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'John Doe', 'USER');

-- --------------------------------------------------------
-- Sample Data for `products`
-- --------------------------------------------------------
INSERT INTO `products` (`name`, `description`, `price`, `image_url`, `category`, `rating`) VALUES
-- Pizza
('Margherita Pizza', 'Classic cheese and tomato pizza.', 1500.00, 'images/pizza-1.png', 'pizza', 4.5),
('Pepperoni Pizza', 'Loaded with spicy pepperoni slices.', 1000.00, 'images/pizza-2.png', 'pizza', 4.8),
('Vegetarian Supreme', 'Mushrooms, onions, peppers, and olives.', 2000.00, 'images/pizza-3.png', 'pizza', 4.2),
('BBQ Chicken Pizza', 'Grilled chicken with tangy BBQ sauce.', 1500.00, 'images/pizza-4.jpg', 'pizza', 4.7), -- Image name guessed, using placeholder if needed
('Hawaiian Pizza', 'Ham and pineapple.', 1800.00, 'images/pizza-5.jpg', 'pizza', 4.3),
('Supreme Pizza', 'Pepperoni, sausage, peppers, onions.', 2000.00, 'images/pizza-6.jpg', 'pizza', 4.6),

-- Pasta
('Spaghetti', 'Classic spaghetti with tomato sauce.', 1300.00, 'images/pasta-1.jpg', 'pasta', 4.5),
('Fettuccine Alfredo', 'Creamy Alfredo sauce.', 1800.00, 'images/pasta-2.jpg', 'pasta', 4.7),
('Penne alla', 'Creamy tomato sauce.', 2300.00, 'images/pasta-3.jpg', 'pasta', 4.4),
('Linguine alle Vongole', 'Clam sauce and garlic.', 3000.00, 'images/pasta-4.jpg', 'pasta', 4.8),
('Ravioli', 'Filled pasta pockets.', 1300.00, 'images/pasta-5.jpg', 'pasta', 4.5),
('Farfalle', 'Bowtie pasta.', 3300.00, 'images/pasta-6.jpg', 'pasta', 4.6),

-- Dessert
('Chocolate Fondue', 'Melted chocolate with dippables.', 4700.00, 'images/dessert-1.jpg', 'dessert', 4.9),
('Tiramisu', 'Coffee-flavored Italian dessert.', 3000.00, 'images/dessert-2.jpg', 'dessert', 4.8),
('Fruit Tart', 'Pastry with fresh fruits.', 2500.00, 'images/dessert-3.jpg', 'dessert', 4.7),
('Molten Lava Cake', 'Warm chocolate cake.', 5000.00, 'images/dessert-4.jpg', 'dessert', 5.0);
