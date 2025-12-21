package com.example.project.services;

import com.example.project.models.Product;
import com.example.project.models.User;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Clear existing products to ensure consistency with hardcoded menu.html
        // IDs/Data
        // In a real prod env, we wouldn't do this, but for this fix it is necessary.
        productRepository.deleteAll();

        if (productRepository.count() == 0) {
            // Pizzas (6 items)
            productRepository.save(new Product(null, "Margherita Pizza",
                    "A classic Italian favorite featuring fresh mozzarella, tomato sauce, basil, and a drizzle of olive oil on a thin crust",
                    new BigDecimal("2000"),
                    "images/pizza-1.png", "pizza", 4.5));
            productRepository.save(new Product(null, "Pepperoni Pizza",
                    "The timeless crowd-pleaser, loaded with savory pepperoni slices, melted mozzarella, and tangy tomato sauce",
                    new BigDecimal("2000"),
                    "images/pizza-2.png", "pizza", 4.8));
            productRepository.save(new Product(null, "BBQ Chicken Pizza",
                    "A mouthwatering blend of barbecue sauce, grilled chicken, red onions, and cilantro, creating a delicious fusion of flavors",
                    new BigDecimal("1500"),
                    "images/pizza-3.png", "pizza", 4.6));
            productRepository.save(new Product(null, "Vegetarian Supreme Pizza",
                    "Packed with a colorful assortment of fresh vegetables such as bell peppers, mushrooms, olives, onions, and tomatoes for a veggie delight",
                    new BigDecimal("2000"),
                    "images/pizza-1.png", "pizza", 4.4));
            productRepository.save(new Product(null, "Hawaiian Pizza",
                    "A tropical twist with ham, pineapple, and mozzarella, offering a sweet and savory combination that's both refreshing and satisfying",
                    new BigDecimal("1800"),
                    "images/pizza-2.png", "pizza", 4.2));
            productRepository.save(new Product(null, "Supreme Pizza",
                    "A tantalizing blend of pepperoni, sausage, bell peppers, onions, mushrooms, and black olives for an unparalleled burst of flavor in every bite.",
                    new BigDecimal("2000"),
                    "images/pizza-3.png", "pizza", 4.9));

            // Pastas (6 items)
            productRepository.save(new Product(null, "Spaghetti",
                    "Classic and versatile, these long, thin strands are a staple in Italian cuisine, commonly paired with various sauces",
                    new BigDecimal("1300"),
                    "images/pasta.jpeg", "pasta", 4.3));
            productRepository.save(new Product(null, "Fettuccine Alfredo",
                    "Wide ribbons of pasta bathed in a rich and creamy Alfredo sauce, creating a luxurious and comforting dish.",
                    new BigDecimal("1800"),
                    "images/pastaex.jpeg", "pasta", 4.7));
            productRepository.save(new Product(null, "Penne alla Vodka",
                    "Short, tube-like pasta tossed in a creamy tomato-based sauce, offering a perfect blend of sweetness and savory notes.",
                    new BigDecimal("2300"),
                    "images/pasta.jpeg", "pasta", 4.5));
            productRepository.save(new Product(null, "Linguine alle Vongole",
                    "Flat, thin noodles served with a flavorful clam sauce, garlic, white wine, and sometimes a hint of chili for a taste of the sea",
                    new BigDecimal("3000"),
                    "images/pastaex.jpeg", "pasta", 4.6));
            productRepository.save(new Product(null, "Ravioli",
                    "Pillow-like pasta pockets filled with a variety of delicious stuffings, such as ricotta and spinach or meat, served with a flavorful sauce.",
                    new BigDecimal("1300"),
                    "images/pasta.jpeg", "pasta", 4.8));
            productRepository.save(new Product(null, "Farfalle",
                    "Delightfully shaped like bowties, these pasta pieces are perfect for catching and holding onto sauces, creating a playful and delicious experience.",
                    new BigDecimal("3300"),
                    "images/pastaex.jpeg", "pasta", 4.4));

            // Desserts (4 items)
            productRepository.save(new Product(null, "Chocolate Fondue",
                    "Dive into indulgence with a communal pot of rich, melted chocolate accompanied by a variety of dippables like strawberries, marshmallows, and pieces of cake",
                    new BigDecimal("4700"),
                    "images/dessertchco.jpeg", "sweet", 4.9));
            productRepository.save(new Product(null, "Tiramisu",
                    "A classic Italian dessert featuring layers of coffee-soaked ladyfingers, mascarpone cheese, and cocoa, creating a heavenly and velvety treat.",
                    new BigDecimal("3000"),
                    "images/Chocolate Tiramisu.jpeg", "sweet", 4.8));
            productRepository.save(new Product(null, "Fruit Tart",
                    "A delightful combination of buttery pastry crust, luscious pastry cream, and a colorful array of fresh fruits, offering a balance of sweetness and tartness.",
                    new BigDecimal("2500"),
                    "images/dessertchco.jpeg", "sweet", 4.5));
            productRepository.save(new Product(null, "Molten Lava Cake",
                    "Experience pure decadence as you dig into a warm and gooey chocolate cake with a melting center, served with a scoop of vanilla ice cream for the perfect dessert indulgence.",
                    new BigDecimal("5000"),
                    "images/Gluten-Free Warm Lava Cakes For Two - Kalejunkie.jpeg", "sweet", 4.9));
        }

        if (userRepository.count() == 0) {
            userRepository.save(new User(null, "admin@example.com", "admin123", "Admin User", "ADMIN"));
            userRepository.save(new User(null, "user@example.com", "user123", "Normal User", "USER"));
        }
    }
}
