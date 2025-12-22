import { cart } from './cart.js';

// Global state


document.addEventListener('DOMContentLoaded', () => {
    initUI();
    initMenu();
    cart.updateBadge(); // Initialize badge
});

function initUI() {
    let navbar = document.querySelector('.navbar');

    // Modal Elements
    const cartModal = document.getElementById('cart-modal');
    const loginModal = document.getElementById('login-modal');
    const signupModal = document.getElementById('signup-modal');
    const checkoutModal = document.getElementById('checkout-modal');
    const profileModal = document.getElementById('profile-modal');
    const foodDetailsModal = document.getElementById('food-details-modal');

    // Order Button in Food Details Modal
    const modalOrderBtn = document.getElementById('modal-order-btn');

    // Navbar toggles
    const menuBtn = document.querySelector('#menu');
    if (menuBtn) {
        menuBtn.onclick = () => {
            navbar.classList.toggle('active');
        }
    }

    // Modal Openers
    const loginBtn = document.querySelector('#login');
    const userBtn = document.querySelector('#user-btn'); // Authenticated user button
    const cartBtn = document.querySelector('#cart');
    const openSignupLink = document.getElementById('open-signup');
    const openLoginLink = document.getElementById('open-login');

    // Modal Closers
    const closeCartBtn = document.getElementById('close-cart');
    const closeLoginBtn = document.getElementById('close-login');
    const closeSignupBtn = document.getElementById('close-signup');
    const closeCheckoutBtn = document.getElementById('close-checkout');
    const closeProfileBtn = document.getElementById('close-profile');
    const closeFoodDetailsBtn = document.getElementById('close-food-details');

    // --- Event Listeners ---

    // Login Button
    if (loginBtn) {
        loginBtn.onclick = (e) => {
            e.preventDefault();
            openModal(loginModal);
        }
    }

    // User Profile Button (Authenticated)
    if (userBtn) {
        userBtn.onclick = (e) => {
            e.preventDefault();
            openModal(profileModal);
        }
    }

    // Cart Button
    if (cartBtn) {
        cartBtn.onclick = (e) => {
            e.preventDefault();
            openModal(cartModal);
            renderCartInModal(); // Render items when opening
        }
    }

    // Switch between Login and Signup
    if (openSignupLink) {
        openSignupLink.onclick = (e) => {
            e.preventDefault();
            closeModal(loginModal);
            openModal(signupModal);
        }
    }

    if (openLoginLink) {
        openLoginLink.onclick = (e) => {
            e.preventDefault();
            closeModal(signupModal);
            openModal(loginModal);
        }
    }

    // Close Buttons
    if (closeCartBtn) closeCartBtn.onclick = () => closeModal(cartModal);
    if (closeLoginBtn) closeLoginBtn.onclick = () => closeModal(loginModal);
    if (closeSignupBtn) closeSignupBtn.onclick = () => closeModal(signupModal);
    if (closeCheckoutBtn) closeCheckoutBtn.onclick = () => closeModal(checkoutModal);
    if (closeProfileBtn) closeProfileBtn.onclick = () => closeModal(profileModal);
    if (closeFoodDetailsBtn) closeFoodDetailsBtn.onclick = () => closeModal(foodDetailsModal);

    // Click outside to close modals
    window.onclick = (e) => {
        if (e.target.classList.contains('modal')) {
            closeModal(e.target);
        }
    }

    window.onscroll = () => {
        if (navbar) navbar.classList.remove('active');
    }

    // Swiper initialization
    if (document.querySelector(".home-slider")) {
        new Swiper(".home-slider", {
            autoplay: { delay: 7500, disableOnInteraction: false },
            grabCursor: true,
            loop: true,
            centeredSlides: true,
            navigation: { nextEl: '.swiper-button-next', prevEl: '.swiper-button-prev' },
        });
    }

    if (document.querySelector(".menu-slider")) {
        new Swiper(".menu-slider", {
            grabCursor: true,
            loop: true,
            autoHeight: true,
            centeredSlides: true,
            spaceBetween: 20,
            pagination: { el: '.swiper-pagination', clickable: true },
        });
    }

    // Initialize Checkout Logic
    initCheckout(checkoutModal, cartModal);

    // Initial setup for the order button
    if (modalOrderBtn) {
        modalOrderBtn.onclick = async (e) => {
            e.preventDefault();
            const productId = modalOrderBtn.dataset.id;
            if (productId) {
                // Determine animation/feedback
                const originalText = modalOrderBtn.innerText;
                modalOrderBtn.innerText = 'Adding...';

                try {
                    await cart.add(productId);
                    modalOrderBtn.innerText = 'Added to Cart!';
                    setTimeout(() => {
                        modalOrderBtn.innerText = originalText;
                        closeModal(foodDetailsModal);
                        openModal(cartModal);
                        renderCartInModal();
                    }, 800);
                } catch (error) {
                    console.error("Failed to add to cart", error);
                    modalOrderBtn.innerText = 'Error!';
                    setTimeout(() => modalOrderBtn.innerText = originalText, 1000);
                }
            } else {
                console.error("No product ID found on order button");
            }
        };
    }
}

function openModal(modal) {
    if (modal) {
        modal.classList.add('active');
        document.body.style.overflow = 'hidden'; // Prevent background scrolling
    }
}

function closeModal(modal) {
    if (modal) {
        modal.classList.remove('active');
        document.body.style.overflow = 'auto';
    }
}

async function renderCartInModal() {
    const container = document.querySelector('#cart-modal .box-container');
    const totalSpan = document.querySelector('#cart-modal .total span');
    if (!container) return;

    container.innerHTML = '<p style="text-align:center; font-size:1.5rem;">Loading...</p>';

    try {
        const cartData = await cart.get();
        container.innerHTML = ''; // Clear loading

        if (!cartData || !cartData.items || cartData.items.length === 0) {
            container.innerHTML = '<p style="text-align:center; font-size:1.5rem;">Your cart is empty.</p>';
            if (totalSpan) totalSpan.innerText = 'Rs.0';
            return;
        }

        let total = 0;
        cartData.items.forEach(item => {
            total += item.product.price * item.quantity;
            const box = document.createElement('div');
            box.classList.add('box');
            box.innerHTML = `
                <i class="fas fa-times remove-item" data-id="${item.id}"></i>
                <img src="${item.product.imageUrl || 'images/pizza-1.png'}" alt="">
                <div class="content">
                    <h3>${item.product.name}</h3>
                    <span> quantity : </span>
                    <div class="qty-controls">
                        <button class="qty-btn minus-btn" data-id="${item.id}">-</button>
                        <span class="qty-display">${item.quantity}</span>
                        <button class="qty-btn plus-btn" data-id="${item.id}">+</button>
                    </div>
                    <br>
                    <span> price : </span>
                    <span class="price">Rs.${item.product.price * item.quantity}</span>
                </div>
            `;
            container.appendChild(box);
        });

        if (totalSpan) totalSpan.innerText = `Rs.${total}`;

    } catch (err) {
        console.error('Error rendering cart:', err);
        container.innerHTML = '<p style="text-align:center; color:red;">Failed to load cart.</p>';
    }
}

// Event Delegation for Cart Modal and Food Details
document.addEventListener('click', async (e) => {
    // Remove Item
    if (e.target.classList.contains('remove-item')) {
        e.preventDefault();
        const itemId = e.target.dataset.id;
        await cart.remove(itemId);
        renderCartInModal();
    }

    // Plus Button
    if (e.target.classList.contains('plus-btn')) {
        e.preventDefault();
        const itemId = e.target.dataset.id;
        await cart.update(itemId, 1); // Send delta +1
        renderCartInModal();
    }

    // Minus Button
    if (e.target.classList.contains('minus-btn')) {
        e.preventDefault();
        const itemId = e.target.dataset.id;
        await cart.update(itemId, -1); // Send delta -1
        renderCartInModal();
    }

    // Add to Cart Button (Event Delegation)
    if (e.target.classList.contains('btn') && e.target.innerText.toLowerCase().includes('add to cart')) {
        e.preventDefault();
        const box = e.target.closest('.box');
        if (box && box.dataset.id) {
            const productId = box.dataset.id;
            console.log('Adding product with ID:', productId); // Debug log
            const result = await cart.add(productId);

            if (result) {
                // If cart modal is open, refresh it
                const cartModal = document.getElementById('cart-modal');
                if (cartModal && cartModal.classList.contains('active')) {
                    renderCartInModal();
                }
            }
        }
    }

    // Gallery Item Click (Food Details)
    if (e.target.closest('.gallery .box .icon')) {
        e.preventDefault();
        const box = e.target.closest('.box');
        const foodDetailsModal = document.getElementById('food-details-modal');

        if (box && foodDetailsModal) {
            // Populate Modal
            document.getElementById('modal-food-image').src = box.dataset.image;
            document.getElementById('modal-food-title').innerText = box.dataset.title;
            document.getElementById('modal-food-flavor').innerText = box.dataset.flavor;
            document.getElementById('modal-food-ingredients').innerText = box.dataset.ingredients;
            document.getElementById('modal-food-allergies').innerText = box.dataset.allergies;

            // Set ID on the order button
            const orderBtn = document.getElementById('modal-order-btn');
            if (orderBtn) {
                orderBtn.dataset.id = box.dataset.id;
            }

            openModal(foodDetailsModal);
        }
    }
});

async function initMenu() {
    // Logic moved to event delegation in document click listener
}

function initCheckout(checkoutModal, cartModal) {
    const checkoutBtn = document.querySelector('.checkout-btn');
    const placeOrderBtn = document.getElementById('place-order-btn');

    if (checkoutBtn) {
        checkoutBtn.onclick = async (e) => {
            e.preventDefault();
            const cartData = await cart.get();
            if (!cartData || !cartData.items || cartData.items.length === 0) {
                alert('Your cart is empty!');
                return;
            }
            closeModal(cartModal);
            openModal(checkoutModal);
            renderCheckoutSummary(cartData);
        };
    }

    if (placeOrderBtn) {
        placeOrderBtn.onclick = async () => {
            await handlePlaceOrder(checkoutModal);
        };
    }
}

function renderCheckoutSummary(cartData) {
    const container = document.querySelector('.summary-items');
    const subtotalEl = document.getElementById('summary-subtotal');
    const deliveryEl = document.getElementById('summary-delivery');
    const taxEl = document.getElementById('summary-tax');
    const totalEl = document.getElementById('summary-total');

    if (!container) return;
    container.innerHTML = '';

    let subtotal = 0;
    cartData.items.forEach(item => {
        const itemTotal = item.product.price * item.quantity;
        subtotal += itemTotal;

        const div = document.createElement('div');
        div.classList.add('summary-item');
        div.innerHTML = `
            <img src="${item.product.imageUrl || 'images/pizza-1.png'}" alt="">
            <div class="info">
                <h5>${item.product.name}</h5>
                <p>Qty: ${item.quantity} x Rs.${item.product.price}</p>
            </div>
            <div class="item-price">Rs.${itemTotal}</div>
        `;
        container.appendChild(div);
    });

    const deliveryFee = 200; // Fixed delivery fee
    const tax = 0; // 0 tax for now
    const total = subtotal + deliveryFee + tax;

    if (subtotalEl) subtotalEl.innerText = `Rs.${subtotal}`;
    if (deliveryEl) deliveryEl.innerText = `Rs.${deliveryFee}`;
    if (taxEl) taxEl.innerText = `Rs.${tax}`;
    if (totalEl) totalEl.innerText = `Rs.${total}`;
}

async function handlePlaceOrder(checkoutModal) {
    const name = document.getElementById('checkout-name').value;
    const phone = document.getElementById('checkout-phone').value;
    const address = document.getElementById('checkout-address').value;

    if (!name || !phone || !address) {
        alert('Please fill in all required fields (Name, Phone, Address)');
        return;
    }

    const placeOrderBtn = document.getElementById('place-order-btn');
    placeOrderBtn.innerText = 'Placing Order...';
    placeOrderBtn.disabled = true;

    try {
        const cartData = await cart.get();
        if (!cartData || !cartData.items || cartData.items.length === 0) {
            alert('Your cart is empty');
            return;
        }

        const orderItems = cartData.items.map(item => ({
            productId: item.product.id,
            quantity: item.quantity
        }));

        const response = await fetch('/api/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                // 'X-CSRF-TOKEN': ... // if CSRF enabled
            },
            // Server gets userId from session, so we don't need to send it here.
            // But OrderRequest expects a "items" list.
            body: JSON.stringify({
                items: orderItems
            })
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Failed to place order');
        }

        const order = await response.json();
        console.log('Order placed:', order);

        // Success
        alert('Your order has been placed successfully! Order ID: ' + order.id);

        // Clear cart
        await cart.clear();

        // Force badge update
        await cart.updateBadge();

        // Close modal
        closeModal(checkoutModal);

        // Reset form
        document.getElementById('checkout-form').reset();

    } catch (error) {
        console.error('Order error:', error);
        alert('Failed to place order: ' + error.message);
    } finally {
        if (placeOrderBtn) {
            placeOrderBtn.innerText = 'Place Order';
            placeOrderBtn.disabled = false;
        }
    }
}
