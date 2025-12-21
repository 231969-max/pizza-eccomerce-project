export const cart = {
    async get() {
        try {
            const response = await fetch('/api/cart');
            if (response.redirected && response.url.includes('login.html')) {
                return null;
            }
            if (response.ok) {
                return await response.json();
            }
            return null;
        } catch (error) {
            console.error('Error fetching cart:', error);
            return null;
        }
    },

    async add(productId, quantity = 1) {
        try {
            const response = await fetch('/api/cart/add', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ productId, quantity })
            });

            if (response.redirected && response.url.includes('login.html')) {
                // User is not logged in and was redirected to login page
                const loginModal = document.getElementById('login-modal');
                if (loginModal) {
                    loginModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                } else {
                    window.location.href = '/login.html';
                }
                return;
            }

            if (response.ok) {
                this.updateBadge();
                alert('Item added to cart!');
                return await response.json();
            } else if (response.status === 401) {
                const loginModal = document.getElementById('login-modal');
                if (loginModal) {
                    loginModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                } else {
                    window.location.href = '/login.html';
                }
            } else {
                alert('Failed to add item');
            }
        } catch (error) {
            console.error('Error adding to cart:', error);
        }
    },

    async update(itemId, change) {
        try {
            const response = await fetch('/api/cart/update', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ itemId, change })
            });

            if (response.redirected && response.url.includes('login.html')) {
                const loginModal = document.getElementById('login-modal');
                if (loginModal) {
                    loginModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                }
                return;
            }

            if (response.ok) {
                this.updateBadge();
                return await response.json();
            }
        } catch (error) {
            console.error('Error updating cart:', error);
        }
    },

    async remove(itemId) {
        try {
            const response = await fetch('/api/cart/remove', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ itemId })
            });

            if (response.redirected && response.url.includes('login.html')) {
                const loginModal = document.getElementById('login-modal');
                if (loginModal) {
                    loginModal.classList.add('active');
                    document.body.style.overflow = 'hidden';
                }
                return;
            }

            if (response.ok) {
                this.updateBadge();
                return await response.json();
            }
        } catch (error) {
            console.error('Error removing item:', error);
        }
    },

    async clear() {
        try {
            const response = await fetch('/api/cart/clear', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.redirected && response.url.includes('login.html')) {
                return false;
            }

            if (response.ok) {
                this.updateBadge();
                return true;
            }
            return false;
        } catch (error) {
            console.error('Error clearing cart:', error);
            return false;
        }
    },

    async updateBadge() {
        try {
            const cartData = await this.get();
            const badge = document.getElementById('cart-badge');
            if (badge) {
                if (cartData && cartData.items && cartData.items.length > 0) {
                    const count = cartData.items.reduce((sum, item) => sum + item.quantity, 0);
                    badge.innerText = count;
                    badge.style.display = 'flex'; // Use flex to center text if needed, or block
                    badge.style.justifyContent = 'center';
                    badge.style.alignItems = 'center';
                } else {
                    badge.style.display = 'none';
                }
            }
        } catch (e) {
            console.error('Error updating badge:', e);
        }
    }
};

// Initialize badge on load if not in a module script (fallback)
// But since we are using type="module" in app.js, we should call this from there.

