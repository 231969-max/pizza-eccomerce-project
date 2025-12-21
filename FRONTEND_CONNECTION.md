# Connecting Frontend to Backend

Your Spring Boot backend is running on `http://localhost:8080`. To connect your frontend (HTML/JS) to it, you can use the browser's built-in `fetch` API.

## 1. CORS Configuration
I have already enabled CORS (Cross-Origin Resource Sharing) in your controllers:
```java
@CrossOrigin(origins = "*")
```
This allows your frontend (even if running on a different port or just as a file) to make requests to the backend.

## 2. Example JavaScript Code

Here are examples of how to call your API endpoints.

### Register User
```javascript
async function registerUser(name, email, password) {
    try {
        const response = await fetch('http://localhost:8080/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                email: email,
                password: password,
                role: 'USER'
            })
        });

        if (!response.ok) {
            throw new Error('Registration failed');
        }

        const data = await response.json();
        console.log('User registered:', data);
        return data;
    } catch (error) {
        console.error('Error:', error);
    }
}
```

### Login User
```javascript
async function loginUser(email, password) {
    try {
        const response = await fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            throw new Error('Login failed');
        }

        const user = await response.json();
        console.log('Logged in:', user);
        // Store user info (e.g., ID) for future requests
        localStorage.setItem('userId', user.id);
        return user;
    } catch (error) {
        console.error('Error:', error);
    }
}
```

### Get All Products
```javascript
async function fetchProducts() {
    try {
        const response = await fetch('http://localhost:8080/api/products');
        const products = await response.json();
        console.log('Products:', products);
        
        // Example: Render products to HTML
        const container = document.getElementById('product-container');
        container.innerHTML = products.map(p => `
            <div class="product-card">
                <img src="${p.imageUrl}" alt="${p.name}">
                <h3>${p.name}</h3>
                <p>${p.description}</p>
                <p>$${p.price}</p>
                <button onclick="addToCart(${p.id})">Add to Cart</button>
            </div>
        `).join('');
        
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}
```

### Create Order
```javascript
async function placeOrder(cartItems) {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert('Please login first');
        return;
    }

    // Transform cart items to match API expectation
    // cartItems should be an array like [{productId: 1, quantity: 2}, ...]
    const orderRequest = {
        userId: parseInt(userId),
        items: cartItems
    };

    try {
        const response = await fetch('http://localhost:8080/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderRequest)
        });

        if (!response.ok) {
            throw new Error('Order failed');
        }

        const order = await response.json();
        console.log('Order placed:', order);
        alert('Order placed successfully!');
    } catch (error) {
        console.error('Error placing order:', error);
    }
}
```
