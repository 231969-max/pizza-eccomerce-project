const API_BASE_URL = 'http://localhost:8080/api';

export const api = {
    async getProducts() {
        const response = await fetch(`${API_BASE_URL}/products`);
        return response.json();
    },

    async getProductsByCategory(category) {
        const response = await fetch(`${API_BASE_URL}/products/category/${category}`);
        return response.json();
    },

    async login(email, password) {
        const response = await fetch(`${API_BASE_URL}/users/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });
        if (!response.ok) throw new Error(await response.text());
        return response.json();
    },

    async register(user) {
        const response = await fetch(`${API_BASE_URL}/users/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(user)
        });
        if (!response.ok) throw new Error(await response.text());
        return response.json();
    },

    async createOrder(orderRequest) {
        const response = await fetch(`${API_BASE_URL}/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderRequest)
        });
        if (!response.ok) throw new Error(await response.text());
        return response.json();
    },

    async sendMessage(message) {
        const response = await fetch(`${API_BASE_URL}/contact`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(message)
        });
        return response.json();
    }
};
